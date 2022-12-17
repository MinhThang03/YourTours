package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.BookingHomeSurchargeDetailCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingSurchargeDetailRepository extends JpaRepository<BookingHomeSurchargeDetailCommand, Long> {
    Optional<BookingHomeSurchargeDetailCommand> findByBookingSurchargeDetailId(UUID bookingSurchargeDetailId);

    List<BookingHomeSurchargeDetailCommand> findAllByBooking(UUID bookingId);
}
