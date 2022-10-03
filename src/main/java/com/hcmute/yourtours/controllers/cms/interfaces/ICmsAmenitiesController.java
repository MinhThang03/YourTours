package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.*;
import com.hcmute.yourtours.libs.model.filter.SimpleFilter;
import com.hcmute.yourtours.models.amenities.AmenitiesDetail;
import com.hcmute.yourtours.models.amenities.AmenitiesInfo;

import java.util.UUID;

public interface ICmsAmenitiesController extends
        ICreateModelController<UUID, AmenitiesDetail>,
        IUpdateModelController<UUID, AmenitiesDetail>,
        IGetDetailByIdController<UUID, AmenitiesDetail>,
        IGetInfoPageController<UUID, AmenitiesInfo, SimpleFilter>,
        IDeleteModelByIdController<UUID> {
}
