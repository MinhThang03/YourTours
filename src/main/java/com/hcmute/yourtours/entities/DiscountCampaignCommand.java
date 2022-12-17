package com.hcmute.yourtours.entities;

import com.hcmute.yourtours.entities.base.NameData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "discount_campaign_id")
public class DiscountCampaignCommand extends NameData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "discount_campaign_id", columnDefinition = "BINARY(16)", unique = true, nullable = false)
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

    @Override
    protected void preWrite() {
        super.preWrite();
        if (discountCampaignId == null) {
            discountCampaignId = UUID.randomUUID();
        }
    }
}
