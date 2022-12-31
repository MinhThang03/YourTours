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
@Table(name = "book_home_surcharge_detail")
public class BookingHomeSurchargeDetailCommand extends Persistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "booking_surcharge_detail", columnDefinition = "BINARY(16)", unique = true, nullable = false)
    private UUID bookingSurchargeDetailId;

    @Column(name = "surcharge_id", columnDefinition = "BINARY(16)")
    private UUID surchargeId;

    @Column(name = "booking", columnDefinition = "BINARY(16)")
    private UUID booking;

    @Column(name = "cost_of_surcharge")
    private Double costOfSurcharge;

    @ManyToOne
    @JoinColumn(name = "booking_relation_id") // thông qua khóa ngoại address_id
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private BookHomesCommand book;

    @ManyToOne
    @JoinColumn(name = "surcharge_relation_id") // thông qua khóa ngoại address_id
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private SurchargeHomeCategoriesCommand surcharge;



    @Override
    protected void preWrite() {
        super.preWrite();
        if (bookingSurchargeDetailId == null) {
            bookingSurchargeDetailId = UUID.randomUUID();
        }
    }
}
