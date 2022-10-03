package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.*;
import com.hcmute.yourtours.libs.model.filter.SimpleFilter;
import com.hcmute.yourtours.models.guest_categories.GuestCategoriesDetail;
import com.hcmute.yourtours.models.guest_categories.GuestCategoriesInfo;

import java.util.UUID;

public interface IGuestCategoriesController extends
        ICreateModelController<UUID, GuestCategoriesDetail>,
        IUpdateModelController<UUID, GuestCategoriesDetail>,
        IGetDetailByIdController<UUID, GuestCategoriesDetail>,
        IGetInfoPageController<UUID, GuestCategoriesInfo, SimpleFilter>,
        IDeleteModelByIdController<UUID> {
}
