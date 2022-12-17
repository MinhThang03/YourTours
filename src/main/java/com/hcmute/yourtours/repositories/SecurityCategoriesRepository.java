package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.SecurityCategoriesCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SecurityCategoriesRepository extends JpaRepository<SecurityCategoriesCommand, Long> {
    Optional<SecurityCategoriesCommand> findBySecurityCategoryId(UUID securityCategoryId);
}
