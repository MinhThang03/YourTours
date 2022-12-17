package com.hcmute.yourtours.entities;

import com.hcmute.yourtours.entities.base.Persistence;
import com.hcmute.yourtours.enums.GuestsCategoryEnum;
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
@Table(name = "book_home_guest_detail")
public class BookingHomeGuestDetailCommand extends Persistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "booking_guest_detail", columnDefinition = "BINARY(16)", unique = true, nullable = false)
    private UUID bookingGuestDetailId;

    @Column(name = "guest_categories")
    @Enumerated(EnumType.STRING)
    private GuestsCategoryEnum guestCategory;

    @Column(name = "number")
    private Integer number;

    @Column(name = "booking", columnDefinition = "BINARY(16)")
    private UUID booking;

    @Override
    protected void preWrite() {
        super.preWrite();
        if (bookingGuestDetailId == null) {
            bookingGuestDetailId = UUID.randomUUID();
        }
    }
}
