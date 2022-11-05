package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.UserEvaluateCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserEvaluateRepository extends JpaRepository<UserEvaluateCommand, Long> {
    Optional<UserEvaluateCommand> findByUserEvaluateId(UUID userEvaluateId);

    Optional<UserEvaluateCommand> findByUserIdAndHomeId(UUID userId, UUID homeId);

    List<UserEvaluateCommand> findAllByHomeIdAndPointIsNotNull(UUID homeId);
}
