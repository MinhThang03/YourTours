package com.hcmute.yourtours.models.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hcmute.yourtours.enums.GenderEnum;
import com.hcmute.yourtours.enums.UserStatusEnum;
import com.hcmute.yourtours.libs.model.BaseData;
import com.hcmute.yourtours.libs.util.constant.RegexUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
@NoArgsConstructor
public class UserInfo extends BaseData<UUID> {

    "^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$"

    @NotNull
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Pattern(regexp = RegexUtils.EMAIL_REGEX, message = "Nhập không đúng định dạng email")
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

    private LocalDate dateOfBirth;

    private GenderEnum gender;

    private String address;

    private String avatar;

    private UserStatusEnum status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String role;
}
