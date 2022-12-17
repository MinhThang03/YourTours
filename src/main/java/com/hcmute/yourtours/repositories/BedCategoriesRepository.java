package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.BedCategoriesCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BedCategoriesRepository extends JpaRepository<BedCategoriesCommand, Long> {
    Optional<BedCategoriesCommand> findByBedCategoryId(UUID bedCategoryId);
}
