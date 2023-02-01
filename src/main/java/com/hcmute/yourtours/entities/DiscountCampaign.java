package com.hcmute.yourtours.entities;

import com.hcmute.yourtours.entities.base.NameData;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "discount_campaign_id")
public class DiscountCampaign extends NameData {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2r")
    @Column(name = "discount_campaign_id", columnDefinition = "varchar(36)")
    @Type(type = "uuid-char")
    private UUID discountCampaignId;

    @Column(name = "code_name")
    private String codeName;

    @Column(name = "percent")
    private Double percent;

    @Column(name = "date_start")
    private LocalDate dateStart;

    @Column(name = "day_end")
    private LocalDate dateEnd;

    @Column(name = "home_id", columnDefinition = "BINARY(16)")
    private UUID homeId;

    @Column(name = "banner")
    private String banner;
}
