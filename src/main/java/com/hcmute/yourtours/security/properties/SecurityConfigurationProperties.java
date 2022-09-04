package com.hcmute.yourtours.security.properties;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@ConfigurationProperties("security")
@Component
public class SecurityConfigurationProperties {
    @JsonProperty("use-webflux")
    private boolean useWebflux;
    @JsonProperty("clients")
    private Map<String, com.hcmute.yourtours.security.properties.SecurityConfigurationProperties.Client> clients;

    @JsonProperty("cors")
    private com.hcmute.yourtours.security.properties.SecurityConfigurationProperties.Cors cors;

    @JsonProperty("path-matcher")
    private com.hcmute.yourtours.security.properties.SecurityConfigurationProperties.PathMatcherConfig pathMatcher;

    @JsonProperty("jwt")
    private com.hcmute.yourtours.security.properties.SecurityConfigurationProperties.Jwt jwt;


    @Data
    public static class PathMatcherConfig {
        @JsonProperty("permit-all-methods")
        private Set<HttpMethod> permitAllMethods = null;

        @JsonProperty("permit-all-path-patterns")
        private Set<String> permitAllPathPatterns = null;

        @JsonProperty("permit-all-map")
        private Map<HttpMethod, Set<String>> permitAllMap = null;
    }

    @Data
    public static class Cors {
        @JsonProperty("allowed-origins")
        private List<String> allowedOrigins;

        @JsonProperty("allowed-methods")
        private List<String> allowedMethods;

        @JsonProperty("allowed-headers")
        private List<String> allowedHeaders;
    }

    @Data
    public static class RestEndpoint {
        @JsonProperty("uri")
        private String uri;
    }

    @Data
    public static class Client {
        @JsonProperty("rest")
        private com.hcmute.yourtours.security.properties.SecurityConfigurationProperties.RestEndpoint rest;
        @JsonProperty("properties")
        private Map<String, String> properties;
    }


    @Data
    public static class Jwt {
        @JsonProperty("secret")
        private String secret;

        @JsonProperty("token-prefix")
        private String tokenPrefix;

        @JsonProperty("jwtExpirationMs")
        private Long jwtExpirationMs;

        @JsonProperty("refreshJwtExpirationMs")
        private Long refreshJwtExpirationMs;

        @JsonProperty("header-string")
        private String headerString;
    }
}
