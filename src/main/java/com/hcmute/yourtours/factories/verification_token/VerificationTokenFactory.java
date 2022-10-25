package com.hcmute.yourtours.factories.verification_token;

import com.hcmute.yourtours.commands.VerificationTokenCommand;
import com.hcmute.yourtours.constant.CornConstant;
import com.hcmute.yourtours.constant.TokenExpirationConstant;
import com.hcmute.yourtours.enums.VerificationTokenTypeEnum;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.verification_token.VerificationTokenDetail;
import com.hcmute.yourtours.models.verification_token.VerificationTokenInfo;
import com.hcmute.yourtours.repositories.VerificationTokenRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class VerificationTokenFactory
        extends BasePersistDataFactory<UUID, VerificationTokenInfo, VerificationTokenDetail, Long, VerificationTokenCommand>
        implements IVerificationTokenFactory {

    private final VerificationTokenRepository verificationTokenRepository;

    protected VerificationTokenFactory(VerificationTokenRepository repository) {
        super(repository);
        this.verificationTokenRepository = repository;
    }

    @Override
    @NonNull
    protected Class<VerificationTokenDetail> getDetailClass() {
        return VerificationTokenDetail.class;
    }

    @Override
    public VerificationTokenCommand createConvertToEntity(VerificationTokenDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return VerificationTokenCommand.builder()
                .token(detail.getToken())
                .userId(detail.getUserId())
                .expiryDate(calculateExpiryDate(TokenExpirationConstant.EXPIRATION_TOKEN_REGISTER))
                .type(detail.getType())
                .build();
    }

    @Override
    public void updateConvertToEntity(VerificationTokenCommand entity, VerificationTokenDetail detail) throws InvalidException {
        entity.setToken(detail.getToken());
        entity.setUserId(detail.getUserId());
        entity.setExpiryDate(detail.getExpiryDate());
        entity.setType(detail.getType());
    }

    @Override
    public VerificationTokenDetail convertToDetail(VerificationTokenCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return VerificationTokenDetail.builder()
                .token(entity.getToken())
                .userId(entity.getUserId())
                .expiryDate(entity.getExpiryDate())
                .id(entity.getVerificationId())
                .type(entity.getType())
                .build();
    }

    @Override
    public VerificationTokenInfo convertToInfo(VerificationTokenCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return VerificationTokenInfo.builder()
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
    public VerificationTokenDetail getVerificationToken(String verificationToken) throws InvalidException {
        Optional<VerificationTokenCommand> optional = verificationTokenRepository.findByToken(verificationToken);
        if (optional.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_VERIFICATION_TOKEN);
        }
        return convertToDetail(optional.get());
    }

    @Override
    public VerificationTokenDetail generateNewVerificationToken(String token) throws InvalidException {
        VerificationTokenDetail vToken = getVerificationToken(token);
        vToken.setExpiryDate(calculateExpiryDate(TokenExpirationConstant.EXPIRATION_TOKEN_REGISTER));
        vToken.setToken(autoGenerateOtp());
        vToken = updateModel(vToken.getId(), vToken);
        return vToken;
    }

    @Override
    public void createPasswordResetTokenForUser(UUID userId, String token) {

    }

    @Override
    public VerificationTokenDetail createVerificationTokenForUser(UUID userId) throws InvalidException {
        VerificationTokenDetail detail = VerificationTokenDetail.builder()
                .userId(userId)
                .token(autoGenerateOtp())
                .type(VerificationTokenTypeEnum.CREATE_ACCOUNT)
                .expiryDate(calculateExpiryDate(TokenExpirationConstant.EXPIRATION_TOKEN_REGISTER))
                .build();
        return createModel(detail);
    }

    @Override
    public boolean validateVerificationCreateAccountToken(String token) throws InvalidException {
        final Optional<VerificationTokenCommand> optional = verificationTokenRepository.findByToken(token);
        if (optional.isEmpty() || !optional.get().getType().equals(VerificationTokenTypeEnum.CREATE_ACCOUNT)) {
            throw new InvalidException(YourToursErrorCode.TOKEN_INVALID);
        }

        final Calendar cal = Calendar.getInstance();
        if ((optional.get().getExpiryDate()
                .getTime() - cal.getTime()
                .getTime()) <= 0) {
            verificationTokenRepository.delete(optional.get());
            throw new InvalidException(YourToursErrorCode.TOKEN_EXPIRED);
        }

        return true;
    }

    @Override
    protected void postCreate(VerificationTokenCommand entity, VerificationTokenDetail detail) throws InvalidException {
        deleteAllExpiredSinceNow();
    }

    @Override
    protected void postUpdate(VerificationTokenCommand entity, VerificationTokenDetail detail) throws InvalidException {
        deleteAllExpiredSinceNow();
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    private VerificationTokenCommand findByVerificationId(UUID verificationId) throws InvalidException {
        Optional<VerificationTokenCommand> entity = verificationTokenRepository.findByVerificationId(verificationId);
        if (entity.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_VERIFICATION_TOKEN);
        }
        return entity.get();
    }

    private String autoGenerateOtp() throws InvalidException {
        SecureRandom random = new SecureRandom();
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

    private void deleteAllExpiredSinceNow() {
        Date date = new Date();
        verificationTokenRepository.deleteAllExpiredSince(date);
    }
}
