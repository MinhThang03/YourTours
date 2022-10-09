package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.HomesCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HomesRepository extends JpaRepository<HomesCommand, Long> {
    Optional<HomesCommand> findByHomeId(UUID homeID);
}
