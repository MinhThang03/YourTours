package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.BookingSurchargeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookingSurchargeDetailRepository extends JpaRepository<BookingSurchargeDetail, UUID> {

    List<BookingSurchargeDetail> findAllByBooking(UUID bookingId);
}
