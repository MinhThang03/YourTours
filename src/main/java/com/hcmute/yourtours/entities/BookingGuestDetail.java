package com.hcmute.yourtours.entities;

import com.hcmute.yourtours.entities.base.Persistence;
import com.hcmute.yourtours.enums.GuestsCategoryEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "book_home_guest_detail")
public class BookingGuestDetail extends Persistence {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2r")
    @Column(name = "booking_guest_detail_id", columnDefinition = "varchar(36)")
    @Type(type = "uuid-char")
    private UUID bookingGuestDetailId;

    @Column(name = "guest_categories")
    @Enumerated(EnumType.STRING)
    private GuestsCategoryEnum guestCategory;

    @Column(name = "number")
    private Integer number;

    @Column(name = "booking", columnDefinition = "BINARY(16)")
    private UUID booking;
}
