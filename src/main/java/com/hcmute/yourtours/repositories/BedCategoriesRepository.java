package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.BedCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BedCategoriesRepository extends JpaRepository<BedCategories, Long> {
    Optional<BedCategories> findByBedCategoryId(UUID bedCategoryId);
}
