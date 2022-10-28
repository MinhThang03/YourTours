package com.hcmute.yourtours.commands;


import com.hcmute.yourtours.commands.base.Audit;
import com.hcmute.yourtours.enums.OtpTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "verification_otp")
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
public class VerificationOtpCommand extends Audit<String> {
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    OtpTypeEnum type;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "verification_id", unique = true, updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID verificationId;
    @Column(name = "token")
    private String token;
    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    private UUID userId;
    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @Override
    protected void preWrite() {
        super.preWrite();
        if (verificationId == null) {
            verificationId = UUID.randomUUID();
        }
    }

}
