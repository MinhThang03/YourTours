package com.hcmute.yourtours.factories.manage_users;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.models.user.UserDetail;

public interface IManageUserFactory {

    UserDetail getDetailByUserName(String userName) throws InvalidException;
}
