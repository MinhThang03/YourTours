package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.AmenitiesCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AmenitiesRepository extends JpaRepository<AmenitiesCommand, Long> {
    Optional<AmenitiesCommand> findByAmenityId(UUID amenityId);
}
