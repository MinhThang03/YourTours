package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.UserCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserCommand, Long> {

    Optional<UserCommand> findByUserId(UUID userId);

    Boolean existsByEmail(String email);

    Optional<UserCommand> findByEmail(String email);
}
