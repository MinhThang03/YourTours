package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.*;
import com.hcmute.yourtours.libs.model.filter.SimpleFilter;
import com.hcmute.yourtours.models.surcharge_home_categories.SurchargeHomeCategoriesDetail;
import com.hcmute.yourtours.models.surcharge_home_categories.SurchargeHomeCategoriesInfo;

import java.util.UUID;

public interface ICmsSurchargeHomeController extends
        ICreateModelController<UUID, SurchargeHomeCategoriesDetail>,
        IUpdateModelController<UUID, SurchargeHomeCategoriesDetail>,
        IGetDetailByIdController<UUID, SurchargeHomeCategoriesDetail>,
        IGetInfoPageController<UUID, SurchargeHomeCategoriesInfo, SimpleFilter>,
        IDeleteModelByIdController<UUID> {
}

