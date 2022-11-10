package com.hcmute.yourtours.factories.user;


import com.hcmute.yourtours.commands.UserCommand;
import com.hcmute.yourtours.constant.RoleConstant;
import com.hcmute.yourtours.constant.SubjectEmailConstant;
import com.hcmute.yourtours.constant.TokenExpirationConstant;
import com.hcmute.yourtours.email.IEmailFactory;
import com.hcmute.yourtours.enums.UserStatusEnum;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.factories.common.IGetUserFromTokenFactory;
import com.hcmute.yourtours.factories.verification_token.IVerificationOtpFactory;
import com.hcmute.yourtours.keycloak.IKeycloakService;
import com.hcmute.yourtours.libs.configuration.IConfigFactory;
import com.hcmute.yourtours.libs.exceptions.ErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.authentication.requests.UserChangePasswordRequest;
import com.hcmute.yourtours.models.authentication.response.ChangePasswordResponse;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.user.UserDetail;
import com.hcmute.yourtours.models.user.UserInfo;
import com.hcmute.yourtours.models.user.request.ForgotPasswordRequest;
import com.hcmute.yourtours.models.verification_token.VerificationOtpDetail;
import com.hcmute.yourtours.repositories.UserRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class UserFactory
        extends BasePersistDataFactory<UUID, UserInfo, UserDetail, Long, UserCommand>
        implements IUserFactory {

    protected final IKeycloakService iKeycloakService;
    private final IConfigFactory configFactory;
    private final UserRepository userRepository;
    private final IEmailFactory iEmailFactory;
    private final IVerificationOtpFactory iVerificationOtpFactory;

    private final IGetUserFromTokenFactory iGetUserFromTokenFactory;

    protected UserFactory(
            UserRepository repository,
            IKeycloakService iKeycloakService,
            IConfigFactory configFactory,
            IEmailFactory iEmailFactory,
            IVerificationOtpFactory iVerificationOtpFactory,
            IGetUserFromTokenFactory iGetUserFromTokenFactory
    ) {
        super(repository);
        this.iKeycloakService = iKeycloakService;
        this.configFactory = configFactory;
        this.userRepository = repository;
        this.iEmailFactory = iEmailFactory;
        this.iVerificationOtpFactory = iVerificationOtpFactory;
        this.iGetUserFromTokenFactory = iGetUserFromTokenFactory;
    }

    @Override
    @NonNull
    protected Class<UserDetail> getDetailClass() {
        return UserDetail.class;
    }

    @Override
    public UserCommand createConvertToEntity(UserDetail detail) {
        if (detail == null) {
            return null;
        }
        return UserCommand.builder()
                .userId(detail.getId())
                .email(detail.getEmail())
                .phoneNumber(detail.getPhoneNumber())
                .fullName(detail.getFullName())
                .dateOfBirth(detail.getDateOfBirth())
                .gender(detail.getGender())
                .address(detail.getAddress())
                .avatar(detail.getAvatar())
                .status(detail.getStatus())
                .role(detail.getRole())
                .createdBy(detail.getCreatedBy())
                .build();
    }

    @Override
    public void updateConvertToEntity(UserCommand entity, UserDetail detail) {
        entity.setPhoneNumber(detail.getPhoneNumber());
        entity.setFullName(detail.getFullName());
        entity.setDateOfBirth(detail.getDateOfBirth());
        entity.setGender(detail.getGender());
        entity.setAddress(detail.getAddress());
        entity.setAvatar(detail.getAvatar());
        entity.setStatus(detail.getStatus());
        entity.setRole(detail.getRole());
    }

    @Override
    public UserDetail convertToDetail(UserCommand entity) {
        if (entity == null) {
            return null;
        }
        return UserDetail.builder()
                .id(entity.getUserId())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .fullName(entity.getFullName())
                .dateOfBirth(entity.getDateOfBirth())
                .gender(entity.getGender())
                .address(entity.getAddress())
                .avatar(entity.getAvatar())
                .status(entity.getStatus())
                .role(entity.getRole())
                .build();
    }

    @Override
    public UserInfo convertToInfo(UserCommand entity) {
        if (entity == null) {
            return null;
        }
        return UserInfo.builder()
                .id(entity.getUserId())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .fullName(entity.getFullName())
                .dateOfBirth(entity.getDateOfBirth())
                .gender(entity.getGender())
                .address(entity.getAddress())
                .avatar(entity.getAvatar())
                .status(entity.getStatus())
                .role(entity.getRole())
                .build();
    }


    @Override
    protected void preCreate(UserDetail detail) throws InvalidException {
        if (checkEmailExist(detail.getEmail())) {
            throw new InvalidException(YourToursErrorCode.USERNAME_EXIST);
        }
    }

    @Override
    protected UserDetail aroundCreate(UserDetail detail) throws InvalidException {
        String userId = createUser(detail);
        detail.setId(UUID.fromString(userId));
        try {

            addRoleToUser(userId, RoleConstant.USER.getRole());
            return super.aroundCreate(detail);
        } catch (Exception e) {
            iKeycloakService.deleteUser(userId);
            throw new InvalidException(YourToursErrorCode.CREATE_USER_FAIL);
        }
    }

    @Override
    protected <F extends BaseFilter> UserDetail aroundGetDetail(UUID id, F filter) throws InvalidException {
        if (id == null && filter == null) {
            id = getCurrentUserId();
        }
        return super.aroundGetDetail(id, filter);
    }


    @Override
    protected Long convertId(UUID id) throws InvalidException {
        Optional<UserCommand> usersCommand = userRepository.findByUserId(id);
        if (usersCommand.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_USER);
        }
        return usersCommand.get().getId();
    }

    private String createUser(UserDetail detail) throws InvalidException {
        return iKeycloakService.createUser(
                detail.getEmail(),
                null,
                detail.getFullName(),
                detail.getEmail(),
                detail.getPassword()
        );
    }


    @Override
    public ChangePasswordResponse userChangePassword(UserChangePasswordRequest request) throws InvalidException {
        try {
            if (!request.getConfirmPassword().equals(request.getNewPassword())) {
                throw new InvalidException(YourToursErrorCode.CONFIRM_PASSWORD_IS_NOT_VALID);
            }

            UUID userId = getCurrentUserId();
            UserCommand entity = userRepository.findByUserId(userId).orElse(null);

            if (entity != null) {
                iKeycloakService.setPassword(userId.toString(), request.getNewPassword());
                iKeycloakService.logout();
                return new ChangePasswordResponse(true);
            }
            return new ChangePasswordResponse(false);
        } catch (InvalidException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidException(YourToursErrorCode.CHANGE_PASSWORD_FAIL);

        }
    }

    @Override
    public UserDetail getDetailByEmail(String email) throws InvalidException {
        UserCommand entity = userRepository.findByEmail(email).orElse(null);
        if (entity == null) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_USER);
        }
        return convertToDetail(entity);
    }

    @Override
    public UserInfo getInfoByEmail(String email) throws InvalidException {
        UserCommand entity = userRepository.findByEmail(email).orElse(null);
        if (entity == null) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_USER);
        }
        return convertToInfo(entity);
    }

    @Override
    public UserDetail getDetailCurrentUser() throws InvalidException {
        Optional<String> optional = iGetUserFromTokenFactory.getCurrentUser();
        if (optional.isEmpty()) {
            throw new InvalidException(ErrorCode.UNAUTHORIZED);
        } else {
            return getDetailModel(UUID.fromString(optional.get()), null);
        }
    }

    @Override
    public UserDetail updateCurrentUser(UserDetail detail) throws InvalidException {
        String userId = iGetUserFromTokenFactory.getCurrentUser().orElse(null);
        if (userId == null) {
            throw new InvalidException(ErrorCode.UNAUTHORIZED);
        } else {
            return updateModel(UUID.fromString(userId), detail);
        }
    }

    @Override
    public void resetPassword(UUID userId, String newPassword, String confirmPassword) throws InvalidException {
        try {
            if (!confirmPassword.equals(newPassword)) {
                throw new InvalidException(YourToursErrorCode.CONFIRM_PASSWORD_IS_NOT_VALID);
            }

            UserCommand entity = userRepository.findByUserId(userId).orElse(null);

            if (entity != null) {
                iKeycloakService.setPassword(userId.toString(), newPassword);
            }
        } catch (InvalidException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidException(YourToursErrorCode.CHANGE_PASSWORD_FAIL);
        }
    }

    @Override
    public SuccessResponse requestForgotPassword(ForgotPasswordRequest request) throws InvalidException {
        Optional<UserCommand> optional = userRepository.findByEmail(request.getEmail());
        if (optional.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_USER);
        } else {
            if (optional.get().getStatus().equals(UserStatusEnum.LOCK)) {
                throw new InvalidException(YourToursErrorCode.ACCOUNT_IS_LOCKED);
            } else {
                iVerificationOtpFactory.createVerificationForgotPasswordOtp(optional.get().getUserId());
                return SuccessResponse.builder()
                        .success(true)
                        .build();
            }
        }
    }

    @Override
    public void checkExistsByUserId(UUID userId) throws InvalidException {
        Optional<UserCommand> optional = userRepository.findByUserId(userId);
        if (optional.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_USER);
        }
    }


    @Override
    protected void postCreate(UserCommand entity, UserDetail detail) throws InvalidException {
        VerificationOtpDetail tokenDetail = iVerificationOtpFactory.createVerificationOtpForUser(entity.getUserId());
        String emailContent = iEmailFactory.getEmailActiveEmailContent
                (
                        entity.getFullName(),
                        String.valueOf(TokenExpirationConstant.EXPIRATION_TOKEN_REGISTER),
                        tokenDetail.getToken()
                );
        iEmailFactory.sendSimpleMessage(entity.getEmail(), SubjectEmailConstant.ACTIVE_ACCOUNT, emailContent);
    }

    private UUID getCurrentUserId() throws InvalidException {
        Optional<UUID> userId = configFactory.auditorAware().getCurrentAuditor();
        if (userId.isEmpty()) {
            throw new InvalidException(ErrorCode.UNAUTHORIZED);
        }
        return userId.get();
    }

    private void addRoleToUser(String userId, String role) throws InvalidException {
        iKeycloakService.addUserClientRoles(userId, List.of(role));
        iKeycloakService.addUserRealmRoles(userId, List.of(role));
    }

    private boolean checkEmailExist(String username) {
        if (username == null) {
            return false;
        }
        return userRepository.existsByEmail(username);
    }

}
