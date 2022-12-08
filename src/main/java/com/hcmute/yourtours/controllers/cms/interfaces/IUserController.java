package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.IGetInfoPageController;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.user.UserInfo;

import java.util.UUID;

public interface IUserController extends IGetInfoPageController<UUID, UserInfo, BaseFilter> {
}
