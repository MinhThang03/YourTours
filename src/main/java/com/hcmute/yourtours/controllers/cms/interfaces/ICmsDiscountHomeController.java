package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.*;
import com.hcmute.yourtours.libs.model.filter.SimpleFilter;
import com.hcmute.yourtours.models.discount_home_categories.DiscountHomeCategoryDetail;
import com.hcmute.yourtours.models.discount_home_categories.DiscountHomeCategoryInfo;

import java.util.UUID;

public interface ICmsDiscountHomeController extends
        ICreateModelController<UUID, DiscountHomeCategoryDetail>,
        IUpdateModelController<UUID, DiscountHomeCategoryDetail>,
        IGetDetailByIdController<UUID, DiscountHomeCategoryDetail>,
        IGetInfoPageController<UUID, DiscountHomeCategoryInfo, SimpleFilter>,
        IDeleteModelByIdController<UUID> {
}

