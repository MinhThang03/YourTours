package com.hcmute.yourtours.models.verification_token;

import com.hcmute.yourtours.enums.VerificationTokenTypeEnum;
import com.hcmute.yourtours.libs.model.BaseData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@SuperBuilder
public class VerificationOtpInfo extends BaseData<UUID> {

    private String token;

    private UUID userId;

    private LocalDateTime expiryDate;

    private VerificationTokenTypeEnum type;
}
