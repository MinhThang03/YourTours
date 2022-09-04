package com.hcmute.yourtours.factory;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.models.requests.LoginRequest;
import com.hcmute.yourtours.models.response.LoginResponse;

public interface IAuthenticationFactory {
    LoginResponse login(LoginRequest loginRequest) throws InvalidException;
}
