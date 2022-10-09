package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.AmenitiesOfHomeCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AmenitiesOfHomeRepository extends JpaRepository<AmenitiesOfHomeCommand, Long> {
    Optional<AmenitiesOfHomeCommand> findByAmenityOfHomeId(UUID amenityOfHomeId);
}
