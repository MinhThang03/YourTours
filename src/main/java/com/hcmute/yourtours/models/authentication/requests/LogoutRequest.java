package com.hcmute.yourtours.models.authentication.requests;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LogoutRequest {
    @NotNull
    @NotBlank
    private String refreshToken;
}
