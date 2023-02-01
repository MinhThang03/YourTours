package com.hcmute.yourtours.entities;

import com.hcmute.yourtours.entities.base.NameData;
import com.hcmute.yourtours.enums.CommonStatusEnum;
import com.hcmute.yourtours.enums.RefundPolicyEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "homes")
public class Homes extends NameData {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2r")
    @Column(name = "home_id", columnDefinition = "varchar(36)")
    @Type(type = "uuid-char")
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

}
