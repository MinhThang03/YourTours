package com.hcmute.yourtours.external_modules.keycloak.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Set;

@Configuration
@ConfigurationProperties("your-tour-profile.security")
@Data
public class KeycloakProperties {

    @JsonProperty("keycloak-realms")
    private Map<String, KeycloakRealm> keycloakRealms;

    @Data
    public static class KeycloakRealm {
        @JsonProperty("realm-name")
        private String realmName;
        @JsonProperty("auth-server-url")
        private String authServerUrl;
        @JsonProperty("client-id")
        private String clientId;
        @JsonProperty("client-secret")
        private String clientSecret;
        @JsonProperty("grant-type")
        private String grantType;
        @JsonProperty("scopes")
        private Set<String> scope;
        @JsonProperty("token-uri")
        private String tokenUri;
        @JsonProperty("credentials")
        private Map<String, Object> credentials;
        @JsonProperty("user-info-uri")
        private String userInfoUri;
        @JsonProperty("token-invalidate-uri")
        private String tokenInvalidateUri;
    }
}
