package com.hcmute.yourtours.entities;


import com.hcmute.yourtours.entities.base.Audit;
import com.hcmute.yourtours.enums.OtpTypeEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "verification_otp")
public class VerificationOtp extends Audit<String> {
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    OtpTypeEnum type;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2r")
    @Column(name = "verification_id", columnDefinition = "varchar(36)")
    @Type(type = "uuid-char")
    private UUID verificationId;
    @Column(name = "token")
    private String token;
    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    private UUID userId;
    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;


}
