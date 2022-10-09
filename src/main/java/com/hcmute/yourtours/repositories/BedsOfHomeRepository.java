package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.BedsOfHomeCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BedsOfHomeRepository extends JpaRepository<BedsOfHomeCommand, Long> {
    Optional<BedsOfHomeCommand> findByBedOfHomeID(UUID bedOfHomeId);
}
