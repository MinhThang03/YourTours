package com.hcmute.yourtours.models.authentication.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserChangePasswordRequest {
    private String password;

    @NotNull
    @NotBlank
    @Size(min = 6, message = "Mật khẩu không được phép ít hơn 6 kí tự")
    private String newPassword;

    @NotNull
    @NotBlank
    @Size(min = 6, message = "Mật khẩu không được phép ít hơn 6 kí tự")
    private String confirmPassword;
}
