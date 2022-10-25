package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.VerificationTokenCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationTokenCommand, Long> {
    Optional<VerificationTokenCommand> findByToken(String token);

    Optional<VerificationTokenCommand> findByUserId(UUID userId);

    Optional<VerificationTokenCommand> findByVerificationId(UUID verificationId);

    Stream<VerificationTokenCommand> findAllByExpiryDateLessThan(Date now);

    void deleteByExpiryDateLessThan(Date now);

    @Query("delete from VerificationTokenCommand t where t.expiryDate < :date")
    void deleteAllExpiredSince(@Param("date") Date now);

    boolean existsByToken(String token);
}
