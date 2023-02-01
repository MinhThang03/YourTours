package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.BookingHomeGuestDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingGuestDetailRepository extends JpaRepository<BookingHomeGuestDetail, Long> {
    Optional<BookingHomeGuestDetail> findByBookingGuestDetailId(UUID bookingGuestDetailId);

    List<BookingHomeGuestDetail> findAllByBooking(UUID bookingId);

    @Query(
            nativeQuery = true,
            value = "select COALESCE(sum(a.number), 0) " +
                    "from book_home_guest_detail a " +
                    "where a.booking = :bookingId "
    )
    Integer sumNumberGuestsOfBooking(UUID bookingId);
}
