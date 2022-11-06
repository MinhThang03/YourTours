package com.hcmute.yourtours.controllers.publics.interfaces;

import com.hcmute.yourtours.libs.controller.IGetInfoPageController;
import com.hcmute.yourtours.models.homes.HomeInfo;
import com.hcmute.yourtours.models.homes.filter.HomeFilter;

import java.util.UUID;

public interface IPublicHomeFactory extends IGetInfoPageController<UUID, HomeInfo, HomeFilter> {
}
