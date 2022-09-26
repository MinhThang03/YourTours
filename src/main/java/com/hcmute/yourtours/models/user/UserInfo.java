package com.hcmute.yourtours.models.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hcmute.yourtours.enums.GenderEnum;
import com.hcmute.yourtours.enums.UserStatusEnum;
import com.hcmute.yourtours.libs.model.BaseData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
@NoArgsConstructor
public class UserInfo extends BaseData<UUID> {

    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
    @Size(min = 6, message = "Mật khẩu ít nhất 6 ký tự")
    private String password;

    @NotNull
    @NotBlank
    private String firstName;

    @NotNull
    @NotBlank
    private String lastName;

    @NotNull
    @NotBlank
    @Email(message = "Nhập đúng định dạng email")
    private String email;

    @NotNull
    @NotBlank
    @Size(max = 10, message = "Chỉ cho phép tối đa 10 số")
    private String phoneNumber;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String phone;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private GenderEnum gender;

    @NotNull
    @NotBlank
    private String address;

    private String avatar;

    private UserStatusEnum status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean emailVerify;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean active;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean firstChangePassword;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String role;
}
