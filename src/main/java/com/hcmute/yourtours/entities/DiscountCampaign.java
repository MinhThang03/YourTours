package com.hcmute.yourtours.entities;

import com.hcmute.yourtours.entities.base.NameData;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

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
@Table(name = "discount_campaign")
public class DiscountCampaign extends NameData {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

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
