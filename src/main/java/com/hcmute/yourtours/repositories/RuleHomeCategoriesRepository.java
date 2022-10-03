package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.RuleHomeCategoriesCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RuleHomeCategoriesRepository extends JpaRepository<RuleHomeCategoriesCommand, Long> {
    Optional<RuleHomeCategoriesCommand> findByRuleCategoryId(UUID ruleCategoryId);
}
