package com.hcmute.yourtours.entities;

import com.hcmute.yourtours.entities.base.NameData;
import com.hcmute.yourtours.enums.CommonStatusEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Audited
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@Table(name = "amenities")
public class Amenities extends NameData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "amenity_id", columnDefinition = "BINARY(16)", unique = true, nullable = false)
    private UUID amenityId;

    @Column(name = "category_id", columnDefinition = "BINARY(16)")
    private UUID categoryId;

    @Column(name = "icon")
    private String icon;

    @Column(name = "set_filter")
    private Boolean setFilter;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CommonStatusEnum status;


    @Override
    protected void preWrite() {
        if (amenityId == null) {
            amenityId = UUID.randomUUID();
        }
    }
}

