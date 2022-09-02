package com.hcmute.yourtours.libs.configuration.properties;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("common.core")
public class CommonConfigurationProperties {
    @JsonProperty("open-api")
    private OpenApiConfigurationProperties openApi;

    @JsonProperty("security")
    private SecurityConfigurationProperties security;

    @JsonProperty("client")
    private ClientConfigurationProperties client;
}
