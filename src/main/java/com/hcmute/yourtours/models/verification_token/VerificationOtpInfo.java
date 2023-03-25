package com.hcmute.yourtours.models.verification_token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmute.yourtours.enums.OtpTypeEnum;
import com.hcmute.yourtours.libs.model.BaseData;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public class VerificationOtpInfo extends BaseData<UUID> {

    private String token;

    private UUID userId;

    private LocalDateTime expiryDate;

    private OtpTypeEnum type;

    @JsonIgnore
    private String createdBy;
}
