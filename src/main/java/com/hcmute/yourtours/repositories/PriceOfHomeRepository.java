package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.PriceOfHomeCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PriceOfHomeRepository extends JpaRepository<PriceOfHomeCommand, Long> {
    Optional<PriceOfHomeCommand> findByPriceOfHomeId(UUID priceOfHomeId);

    @Modifying
    void deleteByHomeIdAndDate(UUID homeId, LocalDate date);

    Optional<PriceOfHomeCommand> findByHomeIdAndDate(UUID homeId, LocalDate date);

}
