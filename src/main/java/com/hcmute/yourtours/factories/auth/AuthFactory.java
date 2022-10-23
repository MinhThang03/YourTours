package com.hcmute.yourtours.factories.auth;


import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.factories.user.IUserFactory;
import com.hcmute.yourtours.keycloak.IKeycloakService;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.models.authentication.requests.LoginRequest;
import com.hcmute.yourtours.models.authentication.requests.RefreshTokenRequest;
import com.hcmute.yourtours.models.authentication.requests.RegisterRequest;
import com.hcmute.yourtours.models.authentication.response.LoginResponse;
import com.hcmute.yourtours.models.authentication.response.LogoutResponse;
import com.hcmute.yourtours.models.authentication.response.RefreshTokenResponse;
import com.hcmute.yourtours.models.authentication.response.RegisterResponse;
import com.hcmute.yourtours.models.user.UserDetail;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthFactory implements IAuthFactory {

    private final IKeycloakService iKeycloakService;
    private final IUserFactory iUserFactory;

    public AuthFactory(IKeycloakService iKeycloakService,
                       @Qualifier("userFactory") IUserFactory iUserFactory) {
        this.iKeycloakService = iKeycloakService;
        this.iUserFactory = iUserFactory;
    }

    @Override
    public LoginResponse login(LoginRequest request) throws InvalidException {
        try {
            // TODO: 10/2/2022
//            UserDetail userDetail = iManageUserFactory.getDetailByUserName(request.getUsername());
            AccessTokenResponse accessTokenResponse = null;
//            if (!userDetail.getStatus().equals(UserStatusEnum.LOCK)) {
            accessTokenResponse = iKeycloakService.getJwt(request.getUsername(), request.getPassword());
//            }
            return LoginResponse.builder()
                    .token(accessTokenResponse)
//                    .isBlocked(userDetail.getStatus().equals(UserStatusEnum.LOCK))
                    .build();
        }
//        catch (InvalidException e) {
//            throw e;
//        }
        catch (Exception e) {
            throw new InvalidException(YourToursErrorCode.LOGIN_FAIL);
        }

    }

    @Override
    public LogoutResponse logout() throws InvalidException {
        try {
            iKeycloakService.logout();
            return new LogoutResponse(true);
        } catch (Exception e) {
            throw new InvalidException(YourToursErrorCode.LOGOUT_FAIL);
        }

    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) throws InvalidException {
        try {
            return new RefreshTokenResponse().setAccessTokenResponse(iKeycloakService.getRefreshToken(request.getRefreshToken()));
        } catch (Exception e) {
            throw new InvalidException(YourToursErrorCode.REFRESH_TOKEN_FAIL);
        }
    }

    @Override
    public RegisterResponse registerAccount(RegisterRequest request) throws InvalidException {
        UserDetail userDetail = UserDetail.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .password(request.getPassword())
                .phoneNumber(request.getPhoneNumber())
                .build();

        iUserFactory.createModel(userDetail);
        return RegisterResponse.builder()
                .success(true)
                .build();
    }
}
