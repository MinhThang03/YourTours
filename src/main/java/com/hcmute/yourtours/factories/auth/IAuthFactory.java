package com.hcmute.yourtours.factories.auth;


import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.models.authentication.requests.LoginRequest;
import com.hcmute.yourtours.models.authentication.requests.RefreshTokenRequest;
import com.hcmute.yourtours.models.authentication.response.LoginResponse;
import com.hcmute.yourtours.models.authentication.response.LogoutResponse;
import com.hcmute.yourtours.models.authentication.response.RefreshTokenResponse;

public interface IAuthFactory {
    LoginResponse login(LoginRequest request) throws InvalidException;

    LogoutResponse logout() throws InvalidException;

    RefreshTokenResponse refreshToken(RefreshTokenRequest request) throws InvalidException;

}
