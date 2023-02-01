package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.PriceOfHome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PriceOfHomeRepository extends JpaRepository<PriceOfHome, Long> {
    Optional<PriceOfHome> findByPriceOfHomeId(UUID priceOfHomeId);

    @Modifying
    void deleteByHomeIdAndDate(UUID homeId, LocalDate date);

    List<PriceOfHome> findAllByHomeIdAndDate(UUID homeId, LocalDate date);

    Optional<PriceOfHome> findByHomeIdAndDate(UUID homeId, LocalDate date);

}
