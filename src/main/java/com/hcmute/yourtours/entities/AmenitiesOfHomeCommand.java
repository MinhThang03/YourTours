package com.hcmute.yourtours.entities;

import com.hcmute.yourtours.entities.base.Persistence;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "amenities_of_home")
public class AmenitiesOfHomeCommand extends Persistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "amenity_of_home_id", columnDefinition = "BINARY(16)", unique = true, nullable = false)
    private UUID amenityOfHomeId;

    @Column(name = "isHave")
    private Boolean isHave;

    @Column(name = "home_id", columnDefinition = "BINARY(16)")
    private UUID homeId;

    @Column(name = "amenity_id", columnDefinition = "BINARY(16)")
    private UUID amenityId;

    @ManyToOne
    @JoinColumn(name = "amenity_relation_id") // thông qua khóa ngoại address_id
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private AmenitiesCommand amenity;

    @ManyToOne
    @JoinColumn(name = "home_relation_id") // thông qua khóa ngoại address_id
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private HomesCommand home;

    @Override
    protected void preWrite() {
        super.preWrite();
        if (amenityOfHomeId == null) {
            amenityOfHomeId = UUID.randomUUID();
        }
    }
}


