package com.hcmute.yourtours.entities;

import com.hcmute.yourtours.entities.base.Persistence;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "amenities_of_home")
public class AmenitiesOfHome extends Persistence {


    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "is_have")
    private Boolean isHave;


    @ManyToOne
    @JoinColumn(
            name = "home_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_amenities_of_home_home"),
            nullable = false,
            insertable = false,
            updatable = false,
            columnDefinition = "BINARY(16)"
    )
    private Homes home;

    @Column(name = "home_id", columnDefinition = "BINARY(16)")
    private UUID homeId;


    @ManyToOne
    @JoinColumn(
            name = "amenity_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_amenities_of_home_amenity"),
            nullable = false,
            insertable = false,
            updatable = false,
            columnDefinition = "BINARY(16)"
    )
    private Amenities amenity;

    @Column(name = "amenity_id", columnDefinition = "BINARY(16)")
    private UUID amenityId;
}


