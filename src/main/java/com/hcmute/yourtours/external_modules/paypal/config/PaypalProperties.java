package com.hcmute.yourtours.external_modules.paypal.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "paypal")
public class PaypalProperties {
    private String mode;
    @JsonProperty("client-id")
    private String clientId;
    @JsonProperty("client-secret")
    private String clientSecret;
}
