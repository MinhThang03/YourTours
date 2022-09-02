package com.hcmute.yourtours.libs.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.hcmute.yourtours.libs.audit.DefaultAuditorAware;
import com.hcmute.yourtours.libs.configuration.intercepter.InitInterceptor;
import com.hcmute.yourtours.libs.configuration.intercepter.InterceptorConfigurer;
import com.hcmute.yourtours.libs.configuration.intercepter.LogInterceptor;
import com.hcmute.yourtours.libs.configuration.intercepter.UserInterceptor;
import com.hcmute.yourtours.libs.factory.IResponseFactory;

import java.util.UUID;

public interface IConfigFactory {
    /**
     * Open API have multiple service url
     */
    OpenAPI openAPI();

    /**
     * Custom operationId
     */
    OperationCustomizer operationIdCustomizer();

    /**
     * Arrange tags by alphabet
     */
    OpenApiCustomiser sortTagsAlphabetically();

    /**
     * Factory create response entity
     */
    IResponseFactory responseFactory();

    /**
     * Create filter chain have config cors, paths matcher and filters
     */
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception;

    /**
     * Add Interceptors for logging, include:
     * <br>
     * {@link InitInterceptor}
     * <br>
     * {@link UserInterceptor}
     * <br>
     * {@link LogInterceptor}
     */
    default WebMvcConfigurer webMvcConfigurer() {
        return new InterceptorConfigurer();
    }

    /**
     * Auto add User UUID for auditor {@link CreatedBy},
     * <br>
     * {@link org.springframework.data.annotation.LastModifiedBy}
     */
    default AuditorAware<UUID> auditorAware() {
        return new DefaultAuditorAware();
    }
}
