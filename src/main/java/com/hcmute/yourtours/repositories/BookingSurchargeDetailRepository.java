package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.BookingSurchargeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingSurchargeDetailRepository extends JpaRepository<BookingSurchargeDetail, Long> {
    Optional<BookingSurchargeDetail> findByBookingSurchargeDetailId(UUID bookingSurchargeDetailId);

    List<BookingSurchargeDetail> findAllByBooking(UUID bookingId);
}
