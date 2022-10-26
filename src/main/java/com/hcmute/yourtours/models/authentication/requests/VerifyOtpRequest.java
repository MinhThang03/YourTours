package com.hcmute.yourtours.models.authentication.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class VerifyOtpRequest {

    @NotNull
    @NotBlank
    private String otp;
}
