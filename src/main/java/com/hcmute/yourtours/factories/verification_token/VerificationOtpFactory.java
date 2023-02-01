package com.hcmute.yourtours.factories.verification_token;

import com.hcmute.yourtours.constant.CornConstant;
import com.hcmute.yourtours.constant.TokenExpirationConstant;
import com.hcmute.yourtours.entities.VerificationOtp;
import com.hcmute.yourtours.enums.OtpTypeEnum;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.libs.util.TimeUtil;
import com.hcmute.yourtours.models.verification_token.VerificationOtpDetail;
import com.hcmute.yourtours.models.verification_token.VerificationOtpInfo;
import com.hcmute.yourtours.repositories.VerificationOtpRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class VerificationOtpFactory
        extends BasePersistDataFactory<UUID, VerificationOtpInfo, VerificationOtpDetail, Long, VerificationOtp>
        implements IVerificationOtpFactory {

    private final VerificationOtpRepository verificationOtpRepository;

    protected VerificationOtpFactory(VerificationOtpRepository repository) {
        super(repository);
        this.verificationOtpRepository = repository;
    }

    @Override
    @NonNull
    protected Class<VerificationOtpDetail> getDetailClass() {
        return VerificationOtpDetail.class;
    }

    @Override
    public VerificationOtp createConvertToEntity(VerificationOtpDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return VerificationOtp.builder()
                .token(detail.getToken())
                .userId(detail.getUserId())
                .expiryDate(calculateExpiryDate(TokenExpirationConstant.EXPIRATION_TOKEN_REGISTER))
                .type(detail.getType())
                .createdBy(detail.getCreatedBy())
                .build();
    }

    @Override
    public void updateConvertToEntity(VerificationOtp entity, VerificationOtpDetail detail) throws InvalidException {
        entity.setToken(detail.getToken());
        entity.setUserId(detail.getUserId());
        entity.setExpiryDate(detail.getExpiryDate());
        entity.setType(detail.getType());
    }

    @Override
    public VerificationOtpDetail convertToDetail(VerificationOtp entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return VerificationOtpDetail.builder()
                .token(entity.getToken())
                .userId(entity.getUserId())
                .expiryDate(entity.getExpiryDate())
                .id(entity.getVerificationId())
                .type(entity.getType())
                .build();
    }

    @Override
    public VerificationOtpInfo convertToInfo(VerificationOtp entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return VerificationOtpInfo.builder()
                .token(entity.getToken())
                .userId(entity.getUserId())
                .expiryDate(entity.getExpiryDate())
                .id(entity.getVerificationId())
                .type(entity.getType())
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        return findByVerificationId(id).getId();
    }


    @Override
    public VerificationOtpDetail getVerificationToken(String verificationToken) throws InvalidException {
        Optional<VerificationOtp> optional = verificationOtpRepository.findByToken(verificationToken);
        if (optional.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_VERIFICATION_TOKEN);
        }
        return convertToDetail(optional.get());
    }

    @Override
    public VerificationOtpDetail generateNewVerificationToken(String token) throws InvalidException {
        VerificationOtpDetail vToken = getVerificationToken(token);
        vToken.setExpiryDate(calculateExpiryDate(TokenExpirationConstant.EXPIRATION_TOKEN_REGISTER));
        vToken.setToken(autoGenerateOtp());
        vToken = updateModel(vToken.getId(), vToken);
        return vToken;
    }

    @Override
    public VerificationOtpDetail createVerificationOtpForUser(UUID userId) throws InvalidException {
        VerificationOtpDetail detail = VerificationOtpDetail.builder()
                .userId(userId)
                .token(autoGenerateOtp())
                .type(OtpTypeEnum.CREATE_ACCOUNT)
                .expiryDate(calculateExpiryDate(TokenExpirationConstant.EXPIRATION_TOKEN_REGISTER))
                .createdBy(userId.toString())
                .build();
        return createModel(detail);
    }

    @Override
    public VerificationOtpDetail createVerificationForgotPasswordOtp(UUID userId) throws InvalidException {
        VerificationOtpDetail detail = VerificationOtpDetail.builder()
                .userId(userId)
                .token(autoGenerateOtp())
                .createdBy(userId.toString())
                .type(OtpTypeEnum.CREATE_ACCOUNT)
                .expiryDate(calculateExpiryDate(TokenExpirationConstant.EXPIRATION_TOKEN_REGISTER))
                .build();
        return createModel(detail);
    }

    @Override
    public VerificationOtpDetail verifyCreateAccountOtp(String token) throws InvalidException {
        return validateVerificationOtp(token, OtpTypeEnum.CREATE_ACCOUNT);
    }

    @Override
    public VerificationOtpDetail resendOtp(UUID userId, OtpTypeEnum otpType) throws InvalidException {
        verificationOtpRepository.deleteAllByUserIdAndType(userId, otpType);
        VerificationOtpDetail detail = VerificationOtpDetail.builder()
                .userId(userId)
                .token(autoGenerateOtp())
                .type(otpType)
                .createdBy(userId.toString())
                .expiryDate(calculateExpiryDate(TokenExpirationConstant.EXPIRATION_TOKEN_REGISTER))
                .build();
        return createModel(detail);
    }

    @Override
    public VerificationOtpDetail verifyForgotPasswordOtp(String token) throws InvalidException {
        return validateVerificationOtp(token, OtpTypeEnum.FORGOT_PASSWORD);
    }


    private LocalDateTime calculateExpiryDate(int expiryTimeInMinutes) {
        return LocalDateTime.now().plusMinutes(expiryTimeInMinutes);
    }

    private VerificationOtp findByVerificationId(UUID verificationId) throws InvalidException {
        Optional<VerificationOtp> entity = verificationOtpRepository.findByVerificationId(verificationId);
        if (entity.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_VERIFICATION_TOKEN);
        }
        return entity.get();
    }

    private String autoGenerateOtp() {
        SecureRandom random = new SecureRandom();
        String timeStrLocalDateTimeNow = TimeUtil.toStringDate(LocalDateTime.now());
        byte[] byteRandom = ("YourTour" + timeStrLocalDateTimeNow).getBytes();
        random.setSeed(byteRandom);

        String result;
        do {
            Optional<String> optional = random.ints(5, 0, 10)
                    .mapToObj(Integer::toString).reduce((a, b) -> a + b);

            result = optional.orElse(null);
        } while (verificationOtpRepository.existsByToken(result) || result == null);
        return result;
    }

    @Scheduled(cron = CornConstant.CORN_DAILY)
    private void autoUpdateStatus() {
        for (int i = 0; i <= 3; i++) {
            try {
                deleteAllExpiredSinceNow();
                log.info("--- JOB SCHEDULE UPDATE STATUS REGISTER SERVICE IS SUCCESSFUL !");
                return;
            } catch (Exception e) {
                log.error("--- JOB SCHEDULE UPDATE STATUS REGISTER SERVICE IS ERROR !");
            }
        }
    }

    @Override
    protected void preCreate(VerificationOtpDetail detail) throws InvalidException {
        deleteAllExpiredSinceNow();
    }

    @Override
    protected void preUpdate(UUID id, VerificationOtpDetail detail) throws InvalidException {
        deleteAllExpiredSinceNow();
    }

    private void deleteAllExpiredSinceNow() {
        verificationOtpRepository.deleteAllOtpExpired(LocalDateTime.now());
    }

    private VerificationOtpDetail validateVerificationOtp(String token, OtpTypeEnum typeOtp) throws InvalidException {
        final Optional<VerificationOtp> optional = verificationOtpRepository.findByToken(token);
        if (optional.isEmpty() || !optional.get().getType().equals(typeOtp)) {
            throw new InvalidException(YourToursErrorCode.TOKEN_INVALID);
        }

        if ((optional.get().getExpiryDate().isBefore(LocalDateTime.now()))) {
            verificationOtpRepository.delete(optional.get());
            throw new InvalidException(YourToursErrorCode.TOKEN_EXPIRED);
        }

        return convertToDetail(optional.get());
    }

}
