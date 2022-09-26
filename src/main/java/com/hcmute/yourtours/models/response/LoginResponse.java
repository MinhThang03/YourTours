package com.hcmute.yourtours.models.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.keycloak.representations.AccessTokenResponse;

@Data
@AllArgsConstructor
@Builder
public class LoginResponse {
    @JsonUnwrapped
    private AccessTokenResponse token;

    private Boolean firstChangePassword;
    private Boolean isBlocked;
}
