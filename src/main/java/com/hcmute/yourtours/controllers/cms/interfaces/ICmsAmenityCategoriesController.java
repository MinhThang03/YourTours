package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.*;
import com.hcmute.yourtours.libs.model.filter.SimpleFilter;
import com.hcmute.yourtours.models.amenity_categories.AmenityCategoriesDetail;
import com.hcmute.yourtours.models.amenity_categories.AmenityCategoriesInfo;

import java.util.UUID;

public interface ICmsAmenityCategoriesController extends
        ICreateModelController<UUID, AmenityCategoriesDetail>,
        IUpdateModelController<UUID, AmenityCategoriesDetail>,
        IGetDetailByIdController<UUID, AmenityCategoriesDetail>,
        IGetInfoPageController<UUID, AmenityCategoriesInfo, SimpleFilter>,
        IDeleteModelByIdController<UUID> {
}

