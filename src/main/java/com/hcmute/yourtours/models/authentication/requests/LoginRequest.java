package com.hcmute.yourtours.models.authentication.requests;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {
    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String password;

    private boolean getLocation;
}
