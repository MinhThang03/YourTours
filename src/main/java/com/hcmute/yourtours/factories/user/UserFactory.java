package com.hcmute.yourtours.factories.user;


import com.hcmute.yourtours.constant.RoleConstant;
import com.hcmute.yourtours.constant.SubjectEmailConstant;
import com.hcmute.yourtours.constant.TokenExpirationConstant;
import com.hcmute.yourtours.entities.User;
import com.hcmute.yourtours.enums.LanguageEnum;
import com.hcmute.yourtours.enums.UserStatusEnum;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.external_modules.email.IEmailFactory;
import com.hcmute.yourtours.external_modules.keycloak.service.IKeycloakService;
import com.hcmute.yourtours.factories.common.IGetUserFromTokenFactory;
import com.hcmute.yourtours.factories.verification_token.IVerificationOtpFactory;
import com.hcmute.yourtours.libs.configuration.IConfigFactory;
import com.hcmute.yourtours.libs.exceptions.ErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.libs.util.constant.RegexUtils;
import com.hcmute.yourtours.models.authentication.requests.UserChangePasswordRequest;
import com.hcmute.yourtours.models.authentication.response.ChangePasswordResponse;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.statistic.admin.projections.StatisticCountProjections;
import com.hcmute.yourtours.models.user.UserDetail;
import com.hcmute.yourtours.models.user.UserInfo;
import com.hcmute.yourtours.models.user.request.ForgotPasswordRequest;
import com.hcmute.yourtours.models.user.request.SettingLanguageRequest;
import com.hcmute.yourtours.models.user.response.SettingLanguageResponse;
import com.hcmute.yourtours.models.verification_token.VerificationOtpDetail;
import com.hcmute.yourtours.repositories.UserRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class UserFactory
        extends BasePersistDataFactory<UUID, UserInfo, UserDetail, UUID, User>
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
    public User createConvertToEntity(UserDetail detail) {
        if (detail == null) {
            return null;
        }
        return User.builder()
                .id(detail.getId())
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
                .language(LanguageEnum.VI)
                .numberOfNotification(0)
                .build();
    }

    @Override
    public void updateConvertToEntity(User entity, UserDetail detail) {
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
    public UserDetail convertToDetail(User entity) {
        if (entity == null) {
            return null;
        }
        return UserDetail.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .fullName(entity.getFullName())
                .dateOfBirth(entity.getDateOfBirth())
                .gender(entity.getGender())
                .address(entity.getAddress())
                .avatar(entity.getAvatar())
                .status(entity.getStatus())
                .role(entity.getRole())
                .isOwner(isOwner(entity.getId()))
                .language(entity.getLanguage())
                .numberOfNotification(entity.getNumberOfNotification())
                .build();
    }

    @Override
    public UserInfo convertToInfo(User entity) {
        if (entity == null) {
            return null;
        }
        return UserInfo.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .fullName(entity.getFullName())
                .dateOfBirth(entity.getDateOfBirth())
                .gender(entity.getGender())
                .address(entity.getAddress())
                .avatar(entity.getAvatar())
                .status(entity.getStatus())
                .role(entity.getRole())
                .language(entity.getLanguage())
                .isOwner(isOwner(entity.getId()))
                .numberOfNotification(entity.getNumberOfNotification())
                .build();
    }

    @Override
    protected void preUpdate(UUID id, UserDetail detail) throws InvalidException {
        if (detail.getPhoneNumber() != null && !detail.getPhoneNumber().matches(RegexUtils.PHONE_REGEX)) {
            throw new InvalidException(YourToursErrorCode.PHONE_NUMBER_IS_UN_FORMAT);
        }
    }

    @Override
    protected void preCreate(UserDetail detail) throws InvalidException {
        if (checkEmailExist(detail.getEmail())) {
            throw new InvalidException(YourToursErrorCode.USERNAME_EXIST);
        }

        if (detail.getPhoneNumber() != null && !detail.getPhoneNumber().matches(RegexUtils.PHONE_REGEX)) {
            throw new InvalidException(YourToursErrorCode.PHONE_NUMBER_IS_UN_FORMAT);
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
            User entity = userRepository.findById(userId).orElse(null);

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
        User entity = userRepository.findByEmail(email).orElse(null);
        if (entity == null) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_USER);
        }
        return convertToDetail(entity);
    }

    @Override
    public UserInfo getInfoByEmail(String email) throws InvalidException {
        User entity = userRepository.findByEmail(email).orElse(null);
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
            UUID userUuid = UUID.fromString(userId);
            UserDetail userDetail = getDetailModel(userUuid, null);
            detail.setRole(userDetail.getRole());
            detail.setStatus(userDetail.getStatus());
            return updateModel(userUuid, detail);
        }
    }

    @Override
    public void resetPassword(UUID userId, String newPassword, String confirmPassword) throws InvalidException {
        try {
            if (!confirmPassword.equals(newPassword)) {
                throw new InvalidException(YourToursErrorCode.CONFIRM_PASSWORD_IS_NOT_VALID);
            }

            User entity = userRepository.findById(userId).orElse(null);

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
        Optional<User> optional = userRepository.findByEmail(request.getEmail());
        if (optional.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_USER);
        } else {
            if (optional.get().getStatus().equals(UserStatusEnum.LOCK)) {
                throw new InvalidException(YourToursErrorCode.ACCOUNT_IS_LOCKED);
            } else {
                iVerificationOtpFactory.createVerificationForgotPasswordOtp(optional.get().getId());
                return SuccessResponse.builder()
                        .success(true)
                        .build();
            }
        }
    }

    @Override
    public void checkExistsByUserId(UUID userId) throws InvalidException {
        Optional<User> optional = userRepository.findById(userId);
        if (optional.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_USER);
        }
    }

    @Override
    public SuccessResponse requestActiveAccount() throws InvalidException {
        UUID userId = iGetUserFromTokenFactory.checkUnAuthorization();
        UserDetail detail = getDetailModel(userId, null);
        requestActiveAccount(detail.getId(), detail.getFullName(), detail.getEmail());
        return SuccessResponse.builder()
                .success(true)
                .build();
    }

    @Override
    public StatisticCountProjections getStatisticCountOfAdmin() {
        return userRepository.getAdminStatisticCount();
    }

    @Override
    public SettingLanguageResponse settingLanguage(SettingLanguageRequest request) throws InvalidException {
        UUID userId = iGetUserFromTokenFactory.checkUnAuthorization();
        User entity = userRepository.findById(userId).orElseThrow(
                () -> new InvalidException(YourToursErrorCode.NOT_FOUND_USER)
        );

        if (!entity.getLanguage().equals(request.getLanguage())) {
            entity.setLanguage(request.getLanguage());
            repository.save(entity);
        }
        return SettingLanguageResponse.builder()
                .language(request.getLanguage())
                .build();
    }

    @Override
    public SuccessResponse resetNumberNotification(UUID userId) throws InvalidException {

        User user = repository.findById(userId).orElseThrow(
                () -> new InvalidException(YourToursErrorCode.NOT_FOUND_USER)
        );

        user.setNumberOfNotification(0);
        repository.save(user);

        return SuccessResponse.builder()
                .success(true)
                .build();
    }

    @Override
    public SuccessResponse resetNumberNotification() throws InvalidException {
        UUID userId = iGetUserFromTokenFactory.checkUnAuthorization();
        return resetNumberNotification(userId);
    }

    @Override
    public User addNumberNotification(UUID userId) throws InvalidException {

        User user = repository.findById(userId).orElseThrow(
                () -> new InvalidException(YourToursErrorCode.NOT_FOUND_USER)
        );

        user.setNumberOfNotification(user.getNumberOfNotification() + 1);
        return repository.save(user);
    }


    @Override
    protected void postCreate(User entity, UserDetail detail) throws InvalidException {
        requestActiveAccount(entity.getId(), entity.getFullName(), entity.getEmail());
    }

    private UUID getCurrentUserId() throws InvalidException {
        Optional<String> userId = configFactory.auditorAware().getCurrentAuditor();
        if (userId.isEmpty()) {
            throw new InvalidException(ErrorCode.UNAUTHORIZED);
        }
        return UUID.fromString(userId.get());
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


    private boolean isOwner(UUID userId) {
        Optional<User> isOwner = userRepository.findByUserIdAndOwner(userId);
        return isOwner.isPresent();
    }

    @Override
    protected <F extends BaseFilter> Page<User> pageQuery(F filter, Integer number, Integer size) throws InvalidException {
        return userRepository.getAll(PageRequest.of(number, size));
    }

    public void requestActiveAccount(UUID userId, String fullName, String email) throws InvalidException {
        VerificationOtpDetail tokenDetail = iVerificationOtpFactory.createVerificationOtpForUser(userId);
        String emailContent = iEmailFactory.getEmailActiveEmailContent
                (
                        fullName,
                        String.valueOf(TokenExpirationConstant.EXPIRATION_TOKEN_REGISTER / 60),
                        tokenDetail.getToken()
                );
        iEmailFactory.sendSimpleMessage(email, SubjectEmailConstant.ACTIVE_ACCOUNT, emailContent);
    }

}
