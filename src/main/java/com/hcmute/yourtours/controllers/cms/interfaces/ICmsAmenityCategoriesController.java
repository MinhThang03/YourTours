package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.*;
import com.hcmute.yourtours.libs.model.filter.SimpleFilter;
import com.hcmute.yourtours.models.amenity_categories.AmenityCategoryDetail;
import com.hcmute.yourtours.models.amenity_categories.AmenityCategoryInfo;

import java.util.UUID;

public interface ICmsAmenityCategoriesController extends
        ICreateModelController<UUID, AmenityCategoryDetail>,
        IUpdateModelController<UUID, AmenityCategoryDetail>,
        IGetDetailByIdController<UUID, AmenityCategoryDetail>,
        IGetInfoPageController<UUID, AmenityCategoryInfo, SimpleFilter>,
        IDeleteModelByIdController<UUID> {
}

