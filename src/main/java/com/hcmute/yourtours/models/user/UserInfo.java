package com.hcmute.yourtours.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hcmute.yourtours.enums.GenderEnum;
import com.hcmute.yourtours.enums.LanguageEnum;
import com.hcmute.yourtours.enums.RoleEnum;
import com.hcmute.yourtours.enums.UserStatusEnum;
import com.hcmute.yourtours.libs.model.BaseData;
import com.hcmute.yourtours.libs.util.constant.RegexUtils;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public class UserInfo extends BaseData<UUID> {

    @JsonIgnore
    @Pattern(regexp = RegexUtils.PASSWORD_REGEX, message = "Nhập không đúng định dạng mật khẩu")
    private String password;

    @NotNull
    @NotBlank
    private String fullName;


    @NotNull
    @NotBlank
    @Pattern(regexp = RegexUtils.EMAIL_REGEX, message = "Nhập không đúng định dạng email")
    private String email;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    private GenderEnum gender;

    private String address;

    private String avatar;

    private UserStatusEnum status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private RoleEnum role;

    @JsonIgnore
    private String createdBy;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean isOwner;

    private LanguageEnum language;
}
