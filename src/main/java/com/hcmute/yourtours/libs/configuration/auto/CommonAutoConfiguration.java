package com.hcmute.yourtours.libs.configuration.auto;

import com.hcmute.yourtours.libs.cache.TimeRedisCacheManager;
import com.hcmute.yourtours.libs.cache.TimeRedisCacheManagerFactory;
import com.hcmute.yourtours.libs.configuration.DefaultConfigFactory;
import com.hcmute.yourtours.libs.configuration.IConfigFactory;
import com.hcmute.yourtours.libs.configuration.properties.CommonConfigurationProperties;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.UUID;

@EnableConfigurationProperties({CommonConfigurationProperties.class, CacheProperties.class})
public class CommonAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    IConfigFactory configFactory(CommonConfigurationProperties properties) {
        return new DefaultConfigFactory(properties);
    }

    @Bean
    @ConditionalOnMissingBean
    OpenAPI openAPI(IConfigFactory factory) {
        return factory.openAPI();
    }

    @Bean
    @ConditionalOnMissingBean
    OperationCustomizer operationIdCustomizer(IConfigFactory factory) {
        return factory.operationIdCustomizer();
    }

    @Bean
    @ConditionalOnMissingBean
    OpenApiCustomiser sortTagsAlphabetically(IConfigFactory factory) {
        return factory.sortTagsAlphabetically();
    }

    @Bean
    @ConditionalOnMissingBean
    IResponseFactory responseFactory(IConfigFactory factory) {
        return factory.responseFactory();
    }

    @Bean
    @ConditionalOnMissingBean
    SecurityFilterChain filterChain(HttpSecurity http, IConfigFactory factory) throws Exception {
        return factory.filterChain(http);
    }

    @Bean
    @ConditionalOnMissingBean
    WebMvcConfigurer webMvcConfigurer(IConfigFactory factory) {
        return factory.webMvcConfigurer();
    }

    @Bean
    @ConditionalOnMissingBean
    public AuditorAware<UUID> auditorAware(IConfigFactory factory) {
        return factory.auditorAware();
    }

    @Bean
    @ConditionalOnMissingBean
    public TimeRedisCacheManagerFactory cacheManagerFactory(
            CacheProperties cacheProperties,
            ObjectProvider<RedisCacheConfiguration> redisCacheConfiguration,
            RedisConnectionFactory redisConnectionFactory,
            ResourceLoader resourceLoader,
            Environment environment
    ) {
        return new TimeRedisCacheManagerFactory(
                cacheProperties, redisCacheConfiguration, redisConnectionFactory, resourceLoader, environment);
    }

    @Bean
    @ConditionalOnMissingBean
    public TimeRedisCacheManager cacheManager(
            TimeRedisCacheManagerFactory factory
    ) {
        return factory.cacheManager();
    }
}
