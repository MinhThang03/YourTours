package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.SecuritiesOfHome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SecuritiesOfHomeRepository extends JpaRepository<SecuritiesOfHome, Long> {
    Optional<SecuritiesOfHome> findBySecurityOfHomeId(UUID securityOfHomeId);
}
