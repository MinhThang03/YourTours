package com.hcmute.yourtours.models.authentication.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hcmute.yourtours.libs.util.constant.RegexUtils;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class RegisterRequest {

    @NotNull
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Pattern(regexp = RegexUtils.PASSWORD_REGEX, message = "Nhập không đúng định dạng mật khẩu")
    private String password;

    @NotNull
    @NotBlank
    private String fullName;

    @NotNull
    @NotBlank
    @Pattern(regexp = RegexUtils.EMAIL_REGEX, message = "Nhập không đúng định dạng email")
    private String email;

    @NotNull
    @NotBlank
    @Pattern(regexp = RegexUtils.PHONE_REGEX, message = "Nhập không đúng định dạng email")
    private String phoneNumber;

}
