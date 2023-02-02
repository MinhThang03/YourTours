package com.hcmute.yourtours.models.authentication.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
import org.keycloak.representations.AccessTokenResponse;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class RefreshTokenResponse {
    @JsonUnwrapped
    private AccessTokenResponse accessTokenResponse;
}


