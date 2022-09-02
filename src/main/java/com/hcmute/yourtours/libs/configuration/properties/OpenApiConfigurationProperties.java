package com.hcmute.yourtours.libs.configuration.properties;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OpenApiConfigurationProperties {
    @JsonProperty("servers")
    private List<String> servers;
}
