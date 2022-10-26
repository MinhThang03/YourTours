package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.VerificationOtpCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationOtpCommand, Long> {
    Optional<VerificationOtpCommand> findByToken(String token);

    Optional<VerificationOtpCommand> findByUserId(UUID userId);

    Optional<VerificationOtpCommand> findByVerificationId(UUID verificationId);

    Stream<VerificationOtpCommand> findAllByExpiryDateLessThan(Date now);

    void deleteByExpiryDateLessThan(Date now);

    @Query("delete from VerificationOtpCommand t where t.expiryDate < :date")
    void deleteAllExpiredSince(@Param("date") Date now);

    boolean existsByToken(String token);
}
