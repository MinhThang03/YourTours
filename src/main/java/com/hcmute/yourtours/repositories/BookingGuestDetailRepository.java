package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.BookingHomeGuestDetailCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingGuestDetailRepository extends JpaRepository<BookingHomeGuestDetailCommand, Long> {
    Optional<BookingHomeGuestDetailCommand> findByBookingGuestDetailId(UUID bookingGuestDetailId);

    List<BookingHomeGuestDetailCommand> findAllByBooking(UUID bookingId);
}
