package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.GuestsOfHomeCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GuestsOfHomeRepository extends JpaRepository<GuestsOfHomeCommand, Long> {
    Optional<GuestsOfHomeCommand> findByGuestOfHomeId(UUID guestOfHomeId);
}
