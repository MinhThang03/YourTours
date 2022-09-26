package com.hcmute.yourtours.models.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {
    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    @Size(min = 8, message = "Độ dài mật khẩu không được phép ít hơn 8 kí tự")
    private String password;
}
