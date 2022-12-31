package com.hcmute.yourtours.entities;

import com.hcmute.yourtours.entities.base.NameData;
import com.hcmute.yourtours.enums.CommonStatusEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "amenities")
public class AmenitiesCommand extends NameData {
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

    @ManyToOne
    @JoinColumn(name = "amenity_category_id") // thông qua khóa ngoại address_id
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private AmenityCategoriesCommand category;

    @OneToMany(mappedBy = "amenity", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<AmenitiesOfHomeCommand> amenitiesOfHome;

    @Override
    protected void preWrite() {
        super.preWrite();
        if (amenityId == null) {
            amenityId = UUID.randomUUID();
        }
    }
}

