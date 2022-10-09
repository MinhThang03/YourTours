package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.*;
import com.hcmute.yourtours.libs.model.filter.SimpleFilter;
import com.hcmute.yourtours.models.guest_categories.GuestCategoryDetail;
import com.hcmute.yourtours.models.guest_categories.GuestCategoryInfo;

import java.util.UUID;

public interface IGuestCategoriesController extends
        ICreateModelController<UUID, GuestCategoryDetail>,
        IUpdateModelController<UUID, GuestCategoryDetail>,
        IGetDetailByIdController<UUID, GuestCategoryDetail>,
        IGetInfoPageController<UUID, GuestCategoryInfo, SimpleFilter>,
        IDeleteModelByIdController<UUID> {
}
