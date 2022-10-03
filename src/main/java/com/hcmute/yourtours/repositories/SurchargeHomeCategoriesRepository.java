package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.SurchargeHomeCategoriesCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SurchargeHomeCategoriesRepository extends JpaRepository<SurchargeHomeCategoriesCommand, Long> {
    Optional<SurchargeHomeCategoriesCommand> findBySurchargeCategoryId(UUID surchargeCategoryId);
}
