package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.*;
import com.hcmute.yourtours.libs.model.filter.SimpleFilter;
import com.hcmute.yourtours.models.amenities.AmenityDetail;
import com.hcmute.yourtours.models.amenities.AmenityInfo;

import java.util.UUID;

public interface ICmsAmenitiesController extends
        ICreateModelController<UUID, AmenityDetail>,
        IUpdateModelController<UUID, AmenityDetail>,
        IGetDetailByIdController<UUID, AmenityDetail>,
        IGetInfoPageController<UUID, AmenityInfo, SimpleFilter>,
        IDeleteModelByIdController<UUID> {
}
