package com.hcmute.yourtours.libs.configuration;

import com.hcmute.yourtours.libs.configuration.properties.CommonConfigurationProperties;
import com.hcmute.yourtours.libs.configuration.properties.OpenApiConfigurationProperties;
import com.hcmute.yourtours.libs.factory.DefaultResponseFactory;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springdoc.core.customizers.OperationCustomizer;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public abstract class BaseConfigFactory implements IConfigFactory {
    protected final OpenApiConfigurationProperties openApiProperties;

    public BaseConfigFactory(
            OpenApiConfigurationProperties openApiServersConfig
    ) {
        this.openApiProperties = openApiServersConfig;
    }

    public BaseConfigFactory(
            CommonConfigurationProperties properties
    ) {
        this(properties.getOpenApi());
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
}
