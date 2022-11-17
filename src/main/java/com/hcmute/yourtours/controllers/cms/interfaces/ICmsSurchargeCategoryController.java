package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.*;
import com.hcmute.yourtours.libs.model.filter.SimpleFilter;
import com.hcmute.yourtours.models.surcharge_home_categories.SurchargeHomeCategoryDetail;
import com.hcmute.yourtours.models.surcharge_home_categories.SurchargeHomeCategoryInfo;

import java.util.UUID;

public interface ICmsSurchargeCategoryController extends
        ICreateModelController<UUID, SurchargeHomeCategoryDetail>,
        IUpdateModelController<UUID, SurchargeHomeCategoryDetail>,
        IGetDetailByIdController<UUID, SurchargeHomeCategoryDetail>,
        IGetInfoPageController<UUID, SurchargeHomeCategoryInfo, SimpleFilter>,
        IDeleteModelByIdController<UUID> {
}

