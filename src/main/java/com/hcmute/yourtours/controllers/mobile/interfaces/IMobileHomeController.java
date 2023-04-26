package com.hcmute.yourtours.controllers.mobile.interfaces;

import com.hcmute.yourtours.libs.controller.IGetInfoPageController;
import com.hcmute.yourtours.models.homes.HomeInfo;
import com.hcmute.yourtours.models.homes.filter.HomeFilter;

import java.util.UUID;

public interface IMobileHomeController extends IGetInfoPageController<UUID, HomeInfo, HomeFilter> {
}
