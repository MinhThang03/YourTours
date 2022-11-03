package com.hcmute.yourtours.controllers.app.interfaces;

import com.hcmute.yourtours.libs.controller.IGetInfoPageController;
import com.hcmute.yourtours.models.homes.HomeInfo;
import com.hcmute.yourtours.models.homes.filter.HomeFilter;

import java.util.UUID;

public interface IAppHomeController extends
        IGetInfoPageController<UUID, HomeInfo, HomeFilter> {
}
