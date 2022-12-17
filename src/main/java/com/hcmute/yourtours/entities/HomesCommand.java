package com.hcmute.yourtours.entities;

import com.hcmute.yourtours.entities.base.NameData;
import com.hcmute.yourtours.enums.CommonStatusEnum;
import com.hcmute.yourtours.enums.RefundPolicyEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "homes")
public class HomesCommand extends NameData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "home_id", columnDefinition = "BINARY(16)", unique = true, nullable = false)
    private UUID homeId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CommonStatusEnum status;

    @Column(name = "wifi")
    private String wifi;

    @Column(name = "pass_wifi")
    private String passWifi;

    @Column(name = "rule_others")
    private String ruleOthers;

    @Column(name = "time_check_in_start")
    private LocalTime timeCheckInStart;

    @Column(name = "time_check_in_end")
    private LocalTime timeCheckInEnd;

    @Column(name = "time_check_in_out")
    private LocalTime timeCheckOut;

    @Column(name = "guide")
    private String guide;

    @Column(name = "cost_per_night_default")
    private Double costPerNightDefault;

    @Column(name = "number_of_guests")
    private Integer numberOfGuests;

    @Column(name = "address_detail")
    private String addressDetail;

    @Column(name = "province_code")
    private Integer provinceCode;

    @Column(name = "ranking")
    private Integer rank;

    @Column(name = "view")
    private Long view;

    @Column(name = "favorite")
    private Long favorite;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "refund_policy")
    @Enumerated(EnumType.STRING)
    private RefundPolicyEnum refundPolicy;

    @Column(name = "average_rate")
    private Double averageRate;

    @Column(name = "number_of_reviews")
    private Long numberOfReviews;

    @Override
    protected void preWrite() {
        super.preWrite();
        if (homeId == null) {
            homeId = UUID.randomUUID();
        }
    }
}
