package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.GuestsOfHome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GuestsOfHomeRepository extends JpaRepository<GuestsOfHome, Long> {
    Optional<GuestsOfHome> findByGuestOfHomeId(UUID guestOfHomeId);
}
