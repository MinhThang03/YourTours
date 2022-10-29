package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.OwnerOfHomeCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OwnerOfHomesRepository extends JpaRepository<OwnerOfHomeCommand, Long> {
    Optional<OwnerOfHomeCommand> findByOwnerOfHomeId(UUID ownerOfHomeId);

    boolean existsByUserIdAndHomeId(UUID userId, UUID homeId);
}
