package com.hcmute.yourtours.libs.configuration.properties;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class ClientConfigurationProperties {
    @JsonProperty("internal")
    private Map<String, Scope> internal;
    @JsonProperty("external")
    private Map<String, Scope> external;
    @JsonProperty("connection")
    private Connection connection;

    @Data
    public static class Connection {
        @JsonProperty("maxIdleConnection")
        private Integer maxIdleConnection = 5;
        @JsonProperty("keepAliveDuration")
        private Integer keepAliveDuration = 60;
        @JsonProperty("connectionTimeout")
        private Integer connectionTimeout = 120;
    }

    @Data
    public static class Scope {
        @JsonProperty("enable")
        private boolean enable;
        @JsonProperty("uri")
        private String uri;
        @JsonProperty("properties")
        private Map<String, String> properties;
    }

}
