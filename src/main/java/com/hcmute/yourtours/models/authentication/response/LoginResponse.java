package com.hcmute.yourtours.models.authentication.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.hcmute.yourtours.models.user.UserInfo;
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
    private UserInfo userInfo;
}
