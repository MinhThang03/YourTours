package com.hcmute.yourtours.controllers.user;

import com.hcmute.yourtours.libs.controller.IGetDetailByIdController;
import com.hcmute.yourtours.models.user.UserDetail;

import java.util.UUID;

public interface IUserController extends
        IGetDetailByIdController<UUID, UserDetail> {
}
