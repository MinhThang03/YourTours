package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.*;
import com.hcmute.yourtours.libs.model.filter.SimpleFilter;
import com.hcmute.yourtours.models.bed_categories.BedCategoryDetail;
import com.hcmute.yourtours.models.bed_categories.BedCategoryInfo;

import java.util.UUID;

public interface ICmsBedCategoriesController extends
        ICreateModelController<UUID, BedCategoryDetail>,
        IUpdateModelController<UUID, BedCategoryDetail>,
        IGetDetailByIdController<UUID, BedCategoryDetail>,
        IGetInfoPageController<UUID, BedCategoryInfo, SimpleFilter>,
        IDeleteModelByIdController<UUID> {
}
