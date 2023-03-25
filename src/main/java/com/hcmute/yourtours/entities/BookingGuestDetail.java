package com.hcmute.yourtours.entities;

import com.hcmute.yourtours.entities.base.Persistence;
import com.hcmute.yourtours.enums.GuestsCategoryEnum;
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
@Table(name = "book_home_guest_detail")
public class BookingGuestDetail extends Persistence {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "guest_categories")
    @Enumerated(EnumType.STRING)
    private GuestsCategoryEnum guestCategory;

    @Column(name = "number")
    private Integer number;


    @ManyToOne
    @JoinColumn(
            name = "booking",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_booking_guest_booking"),
            nullable = false,
            insertable = false,
            updatable = false,
            columnDefinition = "BINARY(16)"
    )
    private BookHomes bookHome;

    @Column(name = "booking", columnDefinition = "BINARY(16)")
    private UUID booking;
}
