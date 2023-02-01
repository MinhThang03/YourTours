package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.SecurityCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SecurityCategoriesRepository extends JpaRepository<SecurityCategories, Long> {
    Optional<SecurityCategories> findBySecurityCategoryId(UUID securityCategoryId);
}
