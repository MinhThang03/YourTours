package com.hcmute.yourtours.factories.verification_token;

import com.hcmute.yourtours.commands.VerificationOtpCommand;
import com.hcmute.yourtours.constant.CornConstant;
import com.hcmute.yourtours.constant.TokenExpirationConstant;
import com.hcmute.yourtours.enums.VerificationTokenTypeEnum;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.libs.util.TimeUtil;
import com.hcmute.yourtours.models.verification_token.VerificationOtpDetail;
import com.hcmute.yourtours.models.verification_token.VerificationOtpInfo;
import com.hcmute.yourtours.repositories.VerificationTokenRepository;
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
        extends BasePersistDataFactory<UUID, VerificationOtpInfo, VerificationOtpDetail, Long, VerificationOtpCommand>
        implements IVerificationOtpFactory {

    private final VerificationTokenRepository verificationTokenRepository;

    protected VerificationOtpFactory(VerificationTokenRepository repository) {
        super(repository);
        this.verificationTokenRepository = repository;
    }

    @Override
    @NonNull
    protected Class<VerificationOtpDetail> getDetailClass() {
        return VerificationOtpDetail.class;
    }

    @Override
    public VerificationOtpCommand createConvertToEntity(VerificationOtpDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return VerificationOtpCommand.builder()
                .token(detail.getToken())
                .userId(detail.getUserId())
                .expiryDate(calculateExpiryDate(TokenExpirationConstant.EXPIRATION_TOKEN_REGISTER))
                .type(detail.getType())
                .build();
    }

    @Override
    public void updateConvertToEntity(VerificationOtpCommand entity, VerificationOtpDetail detail) throws InvalidException {
        entity.setToken(detail.getToken());
        entity.setUserId(detail.getUserId());
        entity.setExpiryDate(detail.getExpiryDate());
        entity.setType(detail.getType());
    }

    @Override
    public VerificationOtpDetail convertToDetail(VerificationOtpCommand entity) throws InvalidException {
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
    public VerificationOtpInfo convertToInfo(VerificationOtpCommand entity) throws InvalidException {
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
        Optional<VerificationOtpCommand> optional = verificationTokenRepository.findByToken(verificationToken);
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
                .type(VerificationTokenTypeEnum.CREATE_ACCOUNT)
                .expiryDate(calculateExpiryDate(TokenExpirationConstant.EXPIRATION_TOKEN_REGISTER))
                .build();
        return createModel(detail);
    }

    @Override
    public VerificationOtpDetail createVerificationForgotPasswordOtp(UUID userId) throws InvalidException {
        VerificationOtpDetail detail = VerificationOtpDetail.builder()
                .userId(userId)
                .token(autoGenerateOtp())
                .type(VerificationTokenTypeEnum.CREATE_ACCOUNT)
                .expiryDate(calculateExpiryDate(TokenExpirationConstant.EXPIRATION_TOKEN_REGISTER))
                .build();
        return createModel(detail);
    }

    @Override
    public VerificationOtpDetail verifyCreateAccountOtp(String token) throws InvalidException {
        return validateVerificationOtp(token, VerificationTokenTypeEnum.CREATE_ACCOUNT);
    }


    private LocalDateTime calculateExpiryDate(int expiryTimeInMinutes) {
        return LocalDateTime.now().plusMinutes(expiryTimeInMinutes);
    }

    private VerificationOtpCommand findByVerificationId(UUID verificationId) throws InvalidException {
        Optional<VerificationOtpCommand> entity = verificationTokenRepository.findByVerificationId(verificationId);
        if (entity.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_VERIFICATION_TOKEN);
        }
        return entity.get();
    }

    private String autoGenerateOtp() throws InvalidException {
        SecureRandom random = new SecureRandom();
        String timeStrLocalDateTimeNow = TimeUtil.toStringDate(LocalDateTime.now());
        byte[] byteRandom = ("YourTour" + timeStrLocalDateTimeNow).getBytes();
        random.setSeed(byteRandom);

        String result;
        do {
            Optional<String> optional = random.ints(5, 0, 10)
                    .mapToObj(Integer::toString).reduce((a, b) -> a + b);

            result = optional.orElse(null);
        } while (verificationTokenRepository.existsByToken(result) || result == null);
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
        verificationTokenRepository.deleteAllOtpExpired(LocalDateTime.now());
    }

    private VerificationOtpDetail validateVerificationOtp(String token, VerificationTokenTypeEnum typeOtp) throws InvalidException {
        final Optional<VerificationOtpCommand> optional = verificationTokenRepository.findByToken(token);
        if (optional.isEmpty() || !optional.get().getType().equals(typeOtp)) {
            throw new InvalidException(YourToursErrorCode.TOKEN_INVALID);
        }

        if ((optional.get().getExpiryDate().isBefore(LocalDateTime.now()))) {
            verificationTokenRepository.delete(optional.get());
            throw new InvalidException(YourToursErrorCode.TOKEN_EXPIRED);
        }

        return convertToDetail(optional.get());
    }

}
