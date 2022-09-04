package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.UserCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserCommand, Long> {
    Optional<UserCommand> findByUsername(String username);

    Optional<UserCommand> findByUserId(UUID userId);
}
