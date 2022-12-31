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
@Table(name = "surcharges_of_home")
public class SurchargesOfHomeCommand extends Persistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "surcharge_of_home_id", columnDefinition = "BINARY(16)", unique = true, nullable = false)
    private UUID surchargeOfHomeId;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "home_id", columnDefinition = "BINARY(16)")
    private UUID homeId;

    @Column(name = "surcharge_category_id", columnDefinition = "BINARY(16)")
    private UUID categoryId;

    @ManyToOne
    @JoinColumn(name = "category_relation_id") // thông qua khóa ngoại address_id
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private SurchargeHomeCategoriesCommand category;

    @ManyToOne
    @JoinColumn(name = "home_relation_id") // thông qua khóa ngoại address_id
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private HomesCommand home;

    @Override
    protected void preWrite() {
        super.preWrite();
        if (surchargeOfHomeId == null) {
            surchargeOfHomeId = UUID.randomUUID();
        }
    }
}
