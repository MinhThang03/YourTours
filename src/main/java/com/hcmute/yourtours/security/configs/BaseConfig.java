package com.hcmute.yourtours.security.configs;


import com.hcmute.yourtours.security.properties.SecurityConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Slf4j
public abstract class BaseConfig {

    protected final SecurityConfigurationProperties securityProperties;

    public BaseConfig(SecurityConfigurationProperties securityProperties) {
        this.securityProperties = securityProperties;
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
                .authenticationEntryPoint(new AuthEntryPointJwt());
    }


    protected void cors(HttpSecurity http) throws Exception {
        CorsConfiguration configuration = buildCorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        http.cors(cors -> cors.configurationSource(source));
    }

    private CorsConfiguration buildCorsConfiguration() {
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
}
