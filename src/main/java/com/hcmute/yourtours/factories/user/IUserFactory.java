package com.hcmute.yourtours.factories.user;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.authentication.requests.UserChangePasswordRequest;
import com.hcmute.yourtours.models.authentication.response.ChangePasswordResponse;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.user.UserDetail;
import com.hcmute.yourtours.models.user.UserInfo;
import com.hcmute.yourtours.models.user.request.ForgotPasswordRequest;

import java.util.UUID;

public interface IUserFactory extends IDataFactory<UUID, UserInfo, UserDetail> {
    ChangePasswordResponse userChangePassword(UserChangePasswordRequest request) throws InvalidException;

    UserDetail getDetailByEmail(String email) throws InvalidException;

    UserInfo getInfoByEmail(String email) throws InvalidException;

    UserDetail getDetailCurrentUser() throws InvalidException;

    UserDetail updateCurrentUser(UserDetail detail) throws InvalidException;

    void resetPassword(UUID userId, String newPassword, String confirmPassword) throws InvalidException;

    SuccessResponse requestForgotPassword(ForgotPasswordRequest request) throws InvalidException;

}
