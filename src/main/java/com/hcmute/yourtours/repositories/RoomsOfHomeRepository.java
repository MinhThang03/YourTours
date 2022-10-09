package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.RoomsOfHomeCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomsOfHomeRepository extends JpaRepository<RoomsOfHomeCommand, Long> {
    Optional<RoomsOfHomeCommand> findByRoomOfHomeId(UUID roomOfHomeId);
}
