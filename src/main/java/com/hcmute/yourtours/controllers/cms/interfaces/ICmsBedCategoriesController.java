package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.*;
import com.hcmute.yourtours.libs.model.filter.SimpleFilter;
import com.hcmute.yourtours.models.bed_categories.BedCategoriesDetail;
import com.hcmute.yourtours.models.bed_categories.BedCategoriesInfo;

import java.util.UUID;

public interface ICmsBedCategoriesController extends
        ICreateModelController<UUID, BedCategoriesDetail>,
        IUpdateModelController<UUID, BedCategoriesDetail>,
        IGetDetailByIdController<UUID, BedCategoriesDetail>,
        IGetInfoPageController<UUID, BedCategoriesInfo, SimpleFilter>,
        IDeleteModelByIdController<UUID> {
}
