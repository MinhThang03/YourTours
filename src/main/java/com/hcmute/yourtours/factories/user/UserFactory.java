package com.hcmute.yourtours.factories.user;


import com.hcmute.yourtours.commands.UserCommand;
import com.hcmute.yourtours.constant.RoleConstant;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.keycloak.IKeycloakService;
import com.hcmute.yourtours.libs.configuration.IConfigFactory;
import com.hcmute.yourtours.libs.exceptions.ErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.requests.UserChangePasswordRequest;
import com.hcmute.yourtours.models.response.ChangePasswordResponse;
import com.hcmute.yourtours.models.user.UserDetail;
import com.hcmute.yourtours.models.user.UserInfo;
import com.hcmute.yourtours.repositories.UserRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserFactory extends BasePersistDataFactory<UUID, UserInfo, UserDetail, Long, UserCommand> implements IUserFactory {

    protected final IKeycloakService iKeycloakService;
    private final IConfigFactory configFactory;
    private final UserRepository userRepository;

    protected UserFactory(
            UserRepository repository,
            IKeycloakService iKeycloakService,
            IConfigFactory configFactory) {
        super(repository);
        this.iKeycloakService = iKeycloakService;
        this.configFactory = configFactory;
        this.userRepository = repository;
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
                .username(detail.getUsername())
                .email(detail.getEmail())
                .phoneNumber(detail.getPhoneNumber())
                .firstName(detail.getFirstName())
                .lastName(detail.getLastName())
                .dateOfBirth(detail.getDateOfBirth())
                .gender(detail.getGender())
                .address(detail.getAddress())
                .avatar(detail.getAvatar())
                .status(detail.getStatus())
                .build();
    }

    @Override
    public void updateConvertToEntity(UserCommand entity, UserDetail detail) {
        entity.setPhoneNumber(detail.getPhoneNumber());
        entity.setFirstName(detail.getFirstName());
        entity.setLastName(detail.getLastName());
        entity.setDateOfBirth(detail.getDateOfBirth());
        entity.setGender(detail.getGender());
        entity.setAddress(detail.getAddress());
        entity.setAvatar(detail.getAvatar());
        entity.setStatus(detail.getStatus());
    }

    @Override
    public UserDetail convertToDetail(UserCommand entity) {
        if (entity == null) {
            return null;
        }
        return UserDetail.builder()
                .id(entity.getUserId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .dateOfBirth(entity.getDateOfBirth())
                .gender(entity.getGender())
                .address(entity.getAddress())
                .avatar(entity.getAvatar())
                .status(entity.getStatus())
                .build();
    }

    @Override
    public UserInfo convertToInfo(UserCommand entity) {
        if (entity == null) {
            return null;
        }
        return UserInfo.builder()
                .id(entity.getUserId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .dateOfBirth(entity.getDateOfBirth())
                .gender(entity.getGender())
                .address(entity.getAddress())
                .avatar(entity.getAvatar())
                .status(entity.getStatus())
                .build();
    }

    @Override
    protected <F extends BaseFilter> Optional<UserCommand> getEntity(UUID id, F filter) throws InvalidException {
        return super.getEntity(id, filter);
    }


    @Override
    protected void preCreate(UserDetail detail) throws InvalidException {
        if (checkUserNameExist(detail.getUsername())) {
            throw new InvalidException(YourToursErrorCode.USERNAME_EXIST);
        }

        if (((UserRepository) repository).existsByEmail(detail.getEmail())) {
            throw new InvalidException(YourToursErrorCode.EMAIL_EXIST);
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
                detail.getUsername(),
                detail.getFirstName(),
                detail.getLastName(),
                detail.getEmail(),
                detail.getPassword()
        );
    }

    private void addRoleToUser(String userId, String role) throws InvalidException {
        iKeycloakService.addUserClientRoles(userId, List.of(role));
        iKeycloakService.addUserRealmRoles(userId, List.of(role));
    }

    private Boolean checkUserNameExist(String username) {
        if (username == null) {
            return false;
        }
        return userRepository.existsByUsername(username);
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
        } catch (Exception e) {
            if (e instanceof InvalidException) {
                throw e;
            } else {
                throw new InvalidException(YourToursErrorCode.CHANGE_PASSWORD_FAIL);
            }
        }
    }


    private UUID getCurrentUserId() throws InvalidException {
        Optional<UUID> userId = configFactory.auditorAware().getCurrentAuditor();
        if (userId.isEmpty()) {
            throw new InvalidException(ErrorCode.UNAUTHORIZED);
        }
        return userId.get();
    }
}
