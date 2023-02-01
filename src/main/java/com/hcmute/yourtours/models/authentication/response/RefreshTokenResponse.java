package com.hcmute.yourtours.models.authentication.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import org.keycloak.representations.AccessTokenResponse;

@Data
public class RefreshTokenResponse {
    @JsonUnwrapped
    private AccessTokenResponse accessTokenResponse;
}


