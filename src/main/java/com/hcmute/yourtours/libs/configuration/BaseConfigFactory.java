package com.hcmute.yourtours.libs.configuration;

import com.hcmute.yourtours.libs.configuration.properties.CommonConfigurationProperties;
import com.hcmute.yourtours.libs.configuration.properties.OpenApiConfigurationProperties;
import com.hcmute.yourtours.libs.configuration.properties.SecurityConfigurationProperties;
import com.hcmute.yourtours.libs.configuration.security.BearerTokenFilter;
import com.hcmute.yourtours.libs.configuration.security.DefaultAuthenticationEntryPoint;
import com.hcmute.yourtours.libs.configuration.security.DefaultJwtDecoderFactory;
import com.hcmute.yourtours.libs.configuration.security.JwtAuthProvider;
import com.hcmute.yourtours.libs.factory.DefaultResponseFactory;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoderFactory;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public abstract class BaseConfigFactory implements IConfigFactory {
    protected final OpenApiConfigurationProperties openApiProperties;
    protected final SecurityConfigurationProperties securityProperties;
    protected JwtDecoderFactory<SecurityConfigurationProperties.Jwt> jwtDecoderFactory = new DefaultJwtDecoderFactory();

    protected BaseConfigFactory(
            OpenApiConfigurationProperties openApiServersConfig,
            SecurityConfigurationProperties securityProperties
    ) {
        this.openApiProperties = openApiServersConfig;
        this.securityProperties = securityProperties;
    }

    protected BaseConfigFactory(
            CommonConfigurationProperties properties
    ) {
        this(properties.getOpenApi(), properties.getSecurity());
    }

    @Override
    public OpenAPI openAPI() {
        return new OpenAPI().servers(
                openApiProperties.getServers().stream().map(serverUrl -> new Server().url(serverUrl)).collect(Collectors.toList())
        );
    }

    @Override
    public OperationCustomizer operationIdCustomizer() {
        return (operation, handlerMethod) -> {
            Class<?> superClazz = handlerMethod.getBeanType().getSuperclass();
            if (Objects.nonNull(superClazz)) {
                String beanName = handlerMethod.getBeanType().getSimpleName();
                operation.setOperationId(String.format("%s_%s", beanName, handlerMethod.getMethod().getName()));
            }
            return operation;
        };
    }

    @Override
    public OpenApiCustomiser sortTagsAlphabetically() {
        return openApi -> {
            if (openApi != null && openApi.getTags() != null) {
                openApi.setTags(
                        openApi.getTags()
                                .stream()
                                .sorted(Comparator.comparing(tag -> StringUtils.stripAccents(tag.getName())))
                                .collect(Collectors.toList()));
            }
        };
    }


    @Override
    public IResponseFactory responseFactory() {
        return new DefaultResponseFactory();
    }

    @Override
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        this.cors(http);
        this.configureAuthorizeRequests(http);
        this.configureFilter(http);
        return http.build();
    }

    protected void configureAuthorizeRequests(HttpSecurity http) throws Exception {
        SecurityConfigurationProperties.PathMatcherConfig pathMatcherConfig = this.securityProperties.getPathMatcher();
        log.debug("PathMatcher {}", pathMatcherConfig);
        if (Objects.nonNull(pathMatcherConfig) && pathMatcherConfig.getPermitAllMethods() != null) {
            for (HttpMethod method : pathMatcherConfig.getPermitAllMethods()) {
                http.authorizeRequests().antMatchers(method).permitAll();
            }
        }

        if (Objects.nonNull(pathMatcherConfig) && pathMatcherConfig.getPermitAllPathPatterns() != null) {
            for (String pattern : pathMatcherConfig.getPermitAllPathPatterns()) {
                http.authorizeRequests().antMatchers(pattern).permitAll();
            }
        }

        if (Objects.nonNull(pathMatcherConfig) && pathMatcherConfig.getPermitAllMap() != null) {
            for (Map.Entry<HttpMethod, Set<String>> entry : pathMatcherConfig.getPermitAllMap().entrySet()) {
                for (String pattern : entry.getValue()) {
                    http.authorizeRequests().antMatchers(entry.getKey(), pattern).permitAll();
                }
            }
        }
        http.authorizeRequests().anyRequest().authenticated().and().exceptionHandling()
                .authenticationEntryPoint(
                        new DefaultAuthenticationEntryPoint()
                )
                .and().authorizeRequests().antMatchers("/stomp-endpoint").permitAll()
        ;
    }

    protected abstract void configureFilter(HttpSecurity http);

    protected void cors(HttpSecurity http) throws Exception {
        CorsConfiguration configuration = buildCorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        http.cors(cors -> cors.configurationSource(source));
    }

    protected CorsConfiguration buildCorsConfiguration() {
        SecurityConfigurationProperties.Cors cors = this.securityProperties.getCors();
        log.debug("Cors {}", cors);
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        if (Objects.nonNull(cors)) {
            if (Objects.nonNull(cors.getAllowedOrigins())) {
                configuration.setAllowedOrigins(cors.getAllowedOrigins());
            }

            if (Objects.nonNull(cors.getAllowedMethods())) {
                configuration.setAllowedMethods(cors.getAllowedMethods());
            }

            if (Objects.nonNull(cors.getAllowedHeaders())) {
                configuration.setAllowedHeaders(cors.getAllowedHeaders());
            }
        }
        return configuration;
    }

    /**
     * Make BearerToken filter from {@link SecurityConfigurationProperties#getOauth2ResourceServer()}
     */
    protected BearerTokenFilter jwtTokenFilter(String name) {
        return new BearerTokenFilter(authenticationProvider(name));
    }

    protected AuthenticationProvider authenticationProvider(String name) {
        JwtDecoder decoder = jwtDecoderFactory.createDecoder(
                securityProperties.getOauth2ResourceServer().get(name)
        );
        return new JwtAuthProvider(decoder);
    }


    /**
     * Make BearerToken filter from {@link SecurityConfigurationProperties#getOauth2ResourceServer()}
     * <br>
     * and pathPattern
     */
    protected BearerTokenFilter jwtTokenFilter(String name, String pathPattern) {
        return new BearerTokenFilter(authenticationProvider(name), pathPattern);
    }

    protected boolean isAuth(String name) {
        return securityProperties.getOauth2ResourceServer() != null && securityProperties.getOauth2ResourceServer().get(name) != null;
    }
}
