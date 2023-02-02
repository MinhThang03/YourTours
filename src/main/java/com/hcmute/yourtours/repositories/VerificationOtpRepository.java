package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.VerificationOtp;
import com.hcmute.yourtours.enums.OtpTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface VerificationOtpRepository extends JpaRepository<VerificationOtp, UUID> {
    Optional<VerificationOtp> findByToken(String token);


    Stream<VerificationOtp> findAllByExpiryDateLessThan(LocalDateTime now);

    void deleteByExpiryDateLessThan(LocalDateTime now);

    @Modifying
    @Query(nativeQuery = true,
            value = "delete from verification_otp  where expiry_date < :date")
    void deleteAllOtpExpired(@Param("date") LocalDateTime date);

    boolean existsByToken(String token);

    @Modifying
    void deleteAllByUserIdAndType(UUID userId, OtpTypeEnum otpType);
}
