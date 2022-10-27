package com.hcmute.yourtours.factories.auth;


import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.models.authentication.requests.LoginRequest;
import com.hcmute.yourtours.models.authentication.requests.RefreshTokenRequest;
import com.hcmute.yourtours.models.authentication.requests.RegisterRequest;
import com.hcmute.yourtours.models.authentication.requests.VerifyOtpRequest;
import com.hcmute.yourtours.models.authentication.response.*;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.user.request.ForgotPasswordRequest;
import com.hcmute.yourtours.models.user.request.ResetPasswordWithOtpRequest;

public interface IAuthFactory {
    LoginResponse login(LoginRequest request) throws InvalidException;

    LogoutResponse logout() throws InvalidException;

    RefreshTokenResponse refreshToken(RefreshTokenRequest request) throws InvalidException;

    RegisterResponse registerAccount(RegisterRequest request) throws InvalidException;

    VerifyOtpResponse activeAccount(VerifyOtpRequest request) throws InvalidException;

    SuccessResponse requestForgotPassword(ForgotPasswordRequest request) throws InvalidException;

    SuccessResponse resetPasswordWithOtp(ResetPasswordWithOtpRequest request) throws InvalidException;
}
