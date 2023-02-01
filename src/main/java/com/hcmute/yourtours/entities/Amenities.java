package com.hcmute.yourtours.entities;

import com.hcmute.yourtours.entities.base.NameData;
import com.hcmute.yourtours.enums.CommonStatusEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@Table(name = "amenities")
public class Amenities extends NameData {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2r")
    @Column(name = "amenity_id", columnDefinition = "varchar(36)")
    @Type(type = "uuid-char")
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
}

