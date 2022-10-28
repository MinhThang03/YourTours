package com.hcmute.yourtours.models.verification_token.request;

import com.hcmute.yourtours.enums.OtpTypeEnum;
import com.hcmute.yourtours.libs.util.constant.RegexUtils;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class ResendVerifyOtp {
    @NotNull
    @NotBlank
    @Pattern(regexp = RegexUtils.EMAIL_REGEX, message = "Không đúng định dạng email")
    private String email;

    @NotNull
    private OtpTypeEnum otpType;
}
