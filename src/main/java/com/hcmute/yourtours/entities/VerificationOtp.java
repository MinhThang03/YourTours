package com.hcmute.yourtours.entities;


import com.hcmute.yourtours.entities.base.Audit;
import com.hcmute.yourtours.enums.OtpTypeEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

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
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "token")
    private String token;


    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_user"),
            nullable = false,
            insertable = false,
            updatable = false,
            columnDefinition = "BINARY(16)"
    )
    private User user;
    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    private UUID userId;
    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;


}
