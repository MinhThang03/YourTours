package com.hcmute.yourtours.models.authentication.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogoutRequest {
    @NotNull
    @NotBlank
    private String refreshToken;
}
