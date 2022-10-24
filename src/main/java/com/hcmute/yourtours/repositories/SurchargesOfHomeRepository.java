package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.SurchargesOfHomeCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SurchargesOfHomeRepository extends JpaRepository<SurchargesOfHomeCommand, Long> {
    Optional<SurchargesOfHomeCommand> findBySurchargeOfHomeId(UUID surchargeOfHomeId);
}
