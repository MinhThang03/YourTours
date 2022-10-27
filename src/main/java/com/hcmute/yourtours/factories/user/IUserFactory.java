package com.hcmute.yourtours.factories.user;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.authentication.requests.UserChangePasswordRequest;
import com.hcmute.yourtours.models.authentication.response.ChangePasswordResponse;
import com.hcmute.yourtours.models.user.UserDetail;
import com.hcmute.yourtours.models.user.UserInfo;

import java.util.UUID;

public interface IUserFactory extends IDataFactory<UUID, UserInfo, UserDetail> {
    ChangePasswordResponse userChangePassword(UserChangePasswordRequest request) throws InvalidException;

    UserDetail getDetailByUserName(String userName) throws InvalidException;

    UserDetail getDetailCurrentUser() throws InvalidException;

    void resetPassword(UUID userId, String newPassword, String confirmPassword) throws InvalidException;
}
