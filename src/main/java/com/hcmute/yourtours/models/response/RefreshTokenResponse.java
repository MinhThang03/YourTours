package com.hcmute.yourtours.models.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import lombok.experimental.Accessors;
import org.keycloak.representations.AccessTokenResponse;

@Data
@Accessors(chain = true)
public class RefreshTokenResponse {
    @JsonUnwrapped
    private AccessTokenResponse accessTokenResponse;
}


