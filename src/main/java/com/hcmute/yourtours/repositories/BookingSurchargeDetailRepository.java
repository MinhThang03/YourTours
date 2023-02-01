package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.BookingHomeSurchargeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingSurchargeDetailRepository extends JpaRepository<BookingHomeSurchargeDetail, Long> {
    Optional<BookingHomeSurchargeDetail> findByBookingSurchargeDetailId(UUID bookingSurchargeDetailId);

    List<BookingHomeSurchargeDetail> findAllByBooking(UUID bookingId);
}
