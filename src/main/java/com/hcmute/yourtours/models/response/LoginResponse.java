package com.hcmute.yourtours.models.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    protected String accessToken;

    protected Long expiresIn;

    protected String refreshToken;

    protected Long refreshExpiresIn;

    protected String tokenType;
}
