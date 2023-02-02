package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.RuleHomeCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RuleHomeCategoriesRepository extends JpaRepository<RuleHomeCategories, UUID> {
}
