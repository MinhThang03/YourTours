package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.BookingGuestDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookingGuestDetailRepository extends JpaRepository<BookingGuestDetail, UUID> {

    List<BookingGuestDetail> findAllByBooking(UUID bookingId);

    @Query(
            nativeQuery = true,
            value = "select COALESCE(sum(a.number), 0) " +
                    "from book_home_guest_detail a " +
                    "where a.booking = :bookingId "
    )
    Integer sumNumberGuestsOfBooking(UUID bookingId);

    @Query(
            value = "select a from BookingGuestDetail a where a.booking = :bookingId order by a.guestCategory "
    )
    List<BookingGuestDetail> findAllByBookingAndSort(@Param("bookingId") UUID bookingId);
}
