package com.hcmute.yourtours.factories.auth;


import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.models.requests.LoginRequest;
import com.hcmute.yourtours.models.requests.RefreshTokenRequest;
import com.hcmute.yourtours.models.response.LoginResponse;
import com.hcmute.yourtours.models.response.LogoutResponse;
import com.hcmute.yourtours.models.response.RefreshTokenResponse;

public interface IAuthFactory {
    LoginResponse login(LoginRequest request) throws InvalidException;

    LogoutResponse logout() throws InvalidException;

    RefreshTokenResponse refreshToken(RefreshTokenRequest request) throws InvalidException;

}
