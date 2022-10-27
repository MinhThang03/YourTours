package com.hcmute.yourtours.factories.auth;


import com.hcmute.yourtours.enums.UserStatusEnum;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.factories.user.IUserFactory;
import com.hcmute.yourtours.factories.verification_token.IVerificationOtpFactory;
import com.hcmute.yourtours.keycloak.IKeycloakService;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.models.authentication.requests.LoginRequest;
import com.hcmute.yourtours.models.authentication.requests.RefreshTokenRequest;
import com.hcmute.yourtours.models.authentication.requests.RegisterRequest;
import com.hcmute.yourtours.models.authentication.requests.VerifyOtpRequest;
import com.hcmute.yourtours.models.authentication.response.*;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.user.UserDetail;
import com.hcmute.yourtours.models.user.request.ForgotPasswordRequest;
import com.hcmute.yourtours.models.user.request.ResetPasswordWithOtpRequest;
import com.hcmute.yourtours.models.verification_token.VerificationOtpDetail;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthFactory implements IAuthFactory {

    private final IKeycloakService iKeycloakService;
    private final IUserFactory iUserFactory;

    private final IVerificationOtpFactory iVerificationOtpFactory;

    public AuthFactory(IKeycloakService iKeycloakService,
                       IUserFactory iUserFactory,
                       IVerificationOtpFactory iVerificationOtpFactory) {
        this.iKeycloakService = iKeycloakService;
        this.iUserFactory = iUserFactory;
        this.iVerificationOtpFactory = iVerificationOtpFactory;
    }

    @Override
    public LoginResponse login(LoginRequest request) throws InvalidException {
        try {
            // TODO: 10/2/2022
//            UserDetail userDetail = iManageUserFactory.getDetailByUserName(request.getUsername());
            AccessTokenResponse accessTokenResponse = null;
//            if (!userDetail.getStatus().equals(UserStatusEnum.LOCK)) {
            accessTokenResponse = iKeycloakService.getJwt(request.getEmail(), request.getPassword());
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

    @Override
    public VerifyOtpResponse activeAccount(VerifyOtpRequest request) throws InvalidException {
        VerificationOtpDetail verifyDetail = iVerificationOtpFactory.verifyCreateAccountOtp(request.getOtp());
        UserDetail userDetail = iUserFactory.getDetailModel(verifyDetail.getUserId(), null);
        userDetail.setStatus(UserStatusEnum.ACTIVE);
        iUserFactory.updateModel(userDetail.getId(), userDetail);
        return VerifyOtpResponse.builder()
                .success(true)
                .build();
    }

    @Override
    public SuccessResponse requestForgotPassword(ForgotPasswordRequest request) throws InvalidException {
        UserDetail userDetail = iUserFactory.getDetailByUserName(request.getEmail());

        if (userDetail.getStatus().equals(UserStatusEnum.LOCK)) {
            throw new InvalidException(YourToursErrorCode.ACCOUNT_IS_LOCKED);
        } else {
            iVerificationOtpFactory.createVerificationForgotPasswordOtp(userDetail.getId());
            return SuccessResponse.builder()
                    .success(true)
                    .build();
        }
    }

    @Override
    public SuccessResponse resetPasswordWithOtp(ResetPasswordWithOtpRequest request) throws InvalidException {
        VerificationOtpDetail verifyDetail = iVerificationOtpFactory.verifyCreateAccountOtp(request.getOtp());
        iUserFactory.resetPassword(verifyDetail.getUserId(), request.getNewPassword(), request.getConfirmPassword());
        return SuccessResponse.builder()
                .success(true)
                .build();
    }


}
