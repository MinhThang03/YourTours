package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.*;
import com.hcmute.yourtours.models.discount_home_categories.DiscountHomeCategoryDetail;
import com.hcmute.yourtours.models.discount_home_categories.DiscountHomeCategoryInfo;
import com.hcmute.yourtours.models.discount_home_categories.filter.DiscountHomeCategoryFilter;

import java.util.UUID;

public interface ICmsDiscountHomeCategoryController extends
        ICreateModelController<UUID, DiscountHomeCategoryDetail>,
        IUpdateModelController<UUID, DiscountHomeCategoryDetail>,
        IGetDetailByIdController<UUID, DiscountHomeCategoryDetail>,
        IGetInfoPageController<UUID, DiscountHomeCategoryInfo, DiscountHomeCategoryFilter>,
        IDeleteModelByIdController<UUID> {
}

