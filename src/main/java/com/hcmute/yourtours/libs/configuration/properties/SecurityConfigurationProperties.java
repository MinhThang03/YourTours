package com.hcmute.yourtours.libs.configuration.properties;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class SecurityConfigurationProperties {

    @JsonProperty("use-webflux")
    private boolean useWebflux;
    @JsonProperty("clients")
    private Map<String, Client> clients;

    @JsonProperty("cors")
    private Cors cors;

    @JsonProperty("path-matcher")
    private PathMatcherConfig pathMatcher;

    @JsonProperty("oauth2-resource-server")
    private Map<String, Jwt> oauth2ResourceServer;

    @JsonProperty("x-api")
    private XApiKeyProperties xApi;

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
        private RestEndpoint rest;
        @JsonProperty("properties")
        private Map<String, String> properties;
    }

    @Data
    public static class Jwt {
        private String jwkSetUri;
        private String issuerUri;
        private List<String> jwsAlgorithms = List.of("RS256");
        private Resource publicKeyLocation;
        private List<String> audiences = new ArrayList<>();

        public String readPublicKey() throws IOException {
            String key = "public-key-location";
            Assert.notNull(this.publicKeyLocation, "PublicKeyLocation must not be null");
            if (!this.publicKeyLocation.exists()) {
                throw new InvalidConfigurationPropertyValueException(key, this.publicKeyLocation,
                        "Public key location does not exist");
            }
            try (InputStream inputStream = this.publicKeyLocation.getInputStream()) {
                return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            }
        }
    }

}
