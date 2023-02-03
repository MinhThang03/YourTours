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
@Table(name = "discount_of_home")
public class DiscountOfHome extends Persistence {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "percent")
    private Double percent;

    @Column(name = "number_date_stay")
    private Integer numberDateStay;

    @Column(name = "number_month_advance")
    private Integer numberMonthAdvance;

    @ManyToOne
    @JoinColumn(
            name = "home_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_discount_of_home_home"),
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
            name = "discount_category_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_discount_of_home_category"),
            nullable = false,
            insertable = false,
            updatable = false,
            columnDefinition = "BINARY(16)"
    )
    private DiscountHomeCategories category;

    @Column(name = "discount_category_id", columnDefinition = "BINARY(16)")
    private UUID categoryId;

}