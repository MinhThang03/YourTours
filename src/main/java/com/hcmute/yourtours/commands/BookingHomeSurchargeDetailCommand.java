package com.hcmute.yourtours.commands;

import com.hcmute.yourtours.commands.base.Persistence;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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

    @Override
    protected void preWrite() {
        super.preWrite();
        if (bookingSurchargeDetailId == null) {
            bookingSurchargeDetailId = UUID.randomUUID();
        }
    }
}
