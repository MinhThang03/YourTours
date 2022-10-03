package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.AmenityCategoriesCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AmenityCategoriesRepository extends JpaRepository<AmenityCategoriesCommand, Long> {
    Optional<AmenityCategoriesCommand> findByAmenityCategoryId(UUID amenityCategoryId);

    Boolean existsByAmenityCategoryId(UUID amenityCategoryId);
}
