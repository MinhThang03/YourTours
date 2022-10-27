package com.hcmute.yourtours.models.user.request;

import com.hcmute.yourtours.libs.util.constant.RegexUtils;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class ForgotPasswordRequest {
    @NotNull
    @NotBlank
    @Pattern(regexp = RegexUtils.EMAIL_REGEX, message = "Nhập không đúng định dạng email")
    private String email;
}
