package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.ICreateModelController;
import com.hcmute.yourtours.models.amenities_of_home.AmenityOfHomeDetail;

import java.util.UUID;

public interface ICmsAmenitiesOfHomeController extends
        ICreateModelController<UUID, AmenityOfHomeDetail> {
}
