package com.hcmute.yourtours.factories.auth;


import com.hcmute.yourtours.constant.SubjectEmailConstant;
import com.hcmute.yourtours.constant.TokenExpirationConstant;
import com.hcmute.yourtours.enums.RoleEnum;
import com.hcmute.yourtours.enums.UserStatusEnum;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.external_modules.email.IEmailFactory;
import com.hcmute.yourtours.external_modules.keycloak.service.IKeycloakService;
import com.hcmute.yourtours.factories.geo_ip_location.IGeoIPLocationFactory;
import com.hcmute.yourtours.factories.user.IUserFactory;
import com.hcmute.yourtours.factories.verification_token.IVerificationOtpFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.models.authentication.requests.LoginRequest;
import com.hcmute.yourtours.models.authentication.requests.RefreshTokenRequest;
import com.hcmute.yourtours.models.authentication.requests.RegisterRequest;
import com.hcmute.yourtours.models.authentication.requests.VerifyOtpRequest;
import com.hcmute.yourtours.models.authentication.response.*;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.user.UserDetail;
import com.hcmute.yourtours.models.user.UserInfo;
import com.hcmute.yourtours.models.user.request.ForgotPasswordRequest;
import com.hcmute.yourtours.models.user.request.ResetPasswordWithOtpRequest;
import com.hcmute.yourtours.models.verification_token.VerificationOtpDetail;
import com.hcmute.yourtours.models.verification_token.request.ResendVerifyOtp;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthFactory implements IAuthFactory {

    private final IKeycloakService iKeycloakService;
    private final IUserFactory iUserFactory;
    private final IEmailFactory iEmailFactory;
    private final IVerificationOtpFactory iVerificationOtpFactory;
    private final IGeoIPLocationFactory iGeoIPLocationFactory;

    public AuthFactory
            (
                    IKeycloakService iKeycloakService,
                    IUserFactory iUserFactory,
                    IEmailFactory iEmailFactory,
                    IVerificationOtpFactory iVerificationOtpFactory,
                    IGeoIPLocationFactory iGeoIPLocationFactory
            ) {
        this.iKeycloakService = iKeycloakService;
        this.iUserFactory = iUserFactory;
        this.iEmailFactory = iEmailFactory;
        this.iVerificationOtpFactory = iVerificationOtpFactory;
        this.iGeoIPLocationFactory = iGeoIPLocationFactory;
    }

    @Override
    public LoginResponse login(LoginRequest request) throws InvalidException {
        try {
            UserInfo userInfo = iUserFactory.getInfoByEmail(request.getEmail());
            AccessTokenResponse accessTokenResponse;
            if (userInfo.getStatus() != null && userInfo.getStatus().equals(UserStatusEnum.LOCK)) {
                throw new InvalidException(YourToursErrorCode.ACCOUNT_IS_LOCKED);
            } else {
                accessTokenResponse = iKeycloakService.getJwt(request.getEmail(), request.getPassword());
            }

            LoginResponse response = LoginResponse.builder()
                    .token(accessTokenResponse)
                    .userInfo(accessTokenResponse == null ? null : userInfo)
                    .build();

            if (request.isGetLocation()) {
                response.setDeviceLocation(iGeoIPLocationFactory.getLocationByCurrentIp());
            }
            return response;

        } catch (InvalidException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidException(YourToursErrorCode.USER_OR_PASSWORD_NOT_CORRECT);
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
            return RefreshTokenResponse.builder()
                    .accessTokenResponse(iKeycloakService.getRefreshToken(request.getRefreshToken()))
                    .build();
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
                .role(RoleEnum.USER)
                .createdBy("REGISTER")
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
        UserDetail userDetail = iUserFactory.getDetailByEmail(request.getEmail());

        if (userDetail.getStatus().equals(UserStatusEnum.LOCK)) {
            throw new InvalidException(YourToursErrorCode.ACCOUNT_IS_LOCKED);
        } else {
            VerificationOtpDetail otpDetail = iVerificationOtpFactory.createVerificationForgotPasswordOtp(userDetail.getId());

            String emailContent = iEmailFactory.getEmailForgotPasswordContent
                    (
                            userDetail.getFullName(),
                            String.valueOf(TokenExpirationConstant.EXPIRATION_TOKEN_REGISTER / 60),
                            otpDetail.getToken()
                    );

            iEmailFactory.sendSimpleMessage(userDetail.getEmail(), SubjectEmailConstant.FORGOT_PASSWORD_OTP, emailContent);
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

    @Override
    public SuccessResponse resendOtp(ResendVerifyOtp request) throws InvalidException {
        UserDetail userDetail = iUserFactory.getDetailByEmail(request.getEmail());
        VerificationOtpDetail otpDetail = iVerificationOtpFactory.resendOtp(userDetail.getId(), request.getOtpType());

        String emailContent = iEmailFactory.getEmailResendOtpContent
                (
                        userDetail.getFullName(),
                        String.valueOf(TokenExpirationConstant.EXPIRATION_TOKEN_REGISTER / 60),
                        otpDetail.getToken()
                );

        iEmailFactory.sendSimpleMessage(userDetail.getEmail(), SubjectEmailConstant.RESEND_OTP, emailContent);
        return SuccessResponse.builder()
                .success(true)
                .build();
    }

}
