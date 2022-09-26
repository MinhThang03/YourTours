package com.hcmute.yourtours.libs.configuration.properties;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class XApiKeyProperties {
    @JsonProperty("services")
    private Map<String, ApiKeyPrefix> services;

    @Data
    public static class ApiKeyPrefix {
        @JsonProperty("prefixes")
        private List<String> prefixes;

        @JsonProperty("keys")
        private List<KeyEnable> keys;
    }

    @Data
    public static class KeyEnable {

        @JsonProperty("value")
        private String value;

        @JsonProperty("enable")
        private Boolean enable;
    }
}
