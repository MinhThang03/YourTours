package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.*;
import com.hcmute.yourtours.libs.model.filter.SimpleFilter;
import com.hcmute.yourtours.models.discount_home_categories.DiscountHomeCategoriesDetail;
import com.hcmute.yourtours.models.discount_home_categories.DiscountHomeCategoriesInfo;

import java.util.UUID;

public interface ICmsDiscountHomeController extends
        ICreateModelController<UUID, DiscountHomeCategoriesDetail>,
        IUpdateModelController<UUID, DiscountHomeCategoriesDetail>,
        IGetDetailByIdController<UUID, DiscountHomeCategoriesDetail>,
        IGetInfoPageController<UUID, DiscountHomeCategoriesInfo, SimpleFilter>,
        IDeleteModelByIdController<UUID> {
}

