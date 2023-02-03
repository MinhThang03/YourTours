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
@Table(name = "surcharges_of_home")
public class SurchargesOfHome extends Persistence {


    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "cost")
    private Double cost;

    @ManyToOne
    @JoinColumn(
            name = "home_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_home"),
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
            name = "surcharge_category_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_category"),
            nullable = false,
            insertable = false,
            updatable = false,
            columnDefinition = "BINARY(16)"
    )
    private SurchargeHomeCategories category;
    @Column(name = "surcharge_category_id", columnDefinition = "BINARY(16)")
    private UUID categoryId;

}
