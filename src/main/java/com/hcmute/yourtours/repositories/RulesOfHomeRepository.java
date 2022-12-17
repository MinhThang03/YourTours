package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.RulesOfHomeCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RulesOfHomeRepository extends JpaRepository<RulesOfHomeCommand, Long> {
    Optional<RulesOfHomeCommand> findByRuleHomeId(UUID ruleOfHomeId);
}
