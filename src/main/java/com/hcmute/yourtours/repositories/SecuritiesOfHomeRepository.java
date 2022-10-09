package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.SecuritiesOfHomeCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SecuritiesOfHomeRepository extends JpaRepository<SecuritiesOfHomeCommand, Long> {
    Optional<SecuritiesOfHomeCommand> findBySecurityOfHomeId(UUID securityOfHomeId);
}
