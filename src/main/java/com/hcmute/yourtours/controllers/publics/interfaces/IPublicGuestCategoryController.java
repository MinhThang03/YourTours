package com.hcmute.yourtours.controllers.publics.interfaces;

import com.hcmute.yourtours.libs.controller.IGetInfoPageController;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.guest_categories.GuestCategoryInfo;

import java.util.UUID;

public interface IPublicGuestCategoryController extends
        IGetInfoPageController<UUID, GuestCategoryInfo, BaseFilter> {
}
