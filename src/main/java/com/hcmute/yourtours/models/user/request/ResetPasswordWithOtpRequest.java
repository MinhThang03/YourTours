package com.hcmute.yourtours.models.user.request;

import com.hcmute.yourtours.libs.util.constant.RegexUtils;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class ResetPasswordWithOtpRequest {
    @NotNull
    @NotBlank
    private String otp;

    @NotNull
    @NotBlank
    @Pattern(regexp = RegexUtils.PASSWORD_REGEX, message = "Nhập không đúng định dạng mật khẩu")
    private String newPassword;


    @NotNull
    @NotBlank
    @Pattern(regexp = RegexUtils.PASSWORD_REGEX, message = "Nhập không đúng định dạng mật khẩu")
    private String confirmPassword;
}
