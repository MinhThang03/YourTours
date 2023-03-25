package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.SurchargeHomeCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SurchargeHomeCategoriesRepository extends JpaRepository<SurchargeHomeCategories, UUID> {
}
