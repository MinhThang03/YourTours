package com.hcmute.yourtours.config;

import com.hcmute.yourtours.constant.RealmConstant;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.keycloak.OAuth2Constants.CLIENT_CREDENTIALS;

@Configuration
@Slf4j
public class KeycloakConfig {

    private final KeycloakProperties securityProperties;

    public KeycloakConfig(KeycloakProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    /**
     * Keycloak config for your tours profile
     *
     * @return Keycloak
     */
    @Bean(name = "keycloakProfile")
    public Keycloak keycloakProfile() {

        return KeycloakBuilder.builder()
                .grantType(CLIENT_CREDENTIALS)
                .serverUrl(securityProperties.getKeycloakRealms().get(RealmConstant.YOURTOUR.getRealmName()).getAuthServerUrl())
                .realm(securityProperties.getKeycloakRealms().get(RealmConstant.YOURTOUR.getRealmName()).getRealmName())
                .clientId(securityProperties.getKeycloakRealms().get(RealmConstant.YOURTOUR.getRealmName()).getClientId())
                .clientSecret(securityProperties.getKeycloakRealms().get(RealmConstant.YOURTOUR.getRealmName()).getClientSecret())
                .build();


    }
}
