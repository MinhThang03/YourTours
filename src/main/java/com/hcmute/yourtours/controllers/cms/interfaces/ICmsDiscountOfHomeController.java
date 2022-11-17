package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.ICreateModelController;
import com.hcmute.yourtours.models.discount_of_home.DiscountOfHomeDetail;

import java.util.UUID;

public interface ICmsDiscountOfHomeController extends
        ICreateModelController<UUID, DiscountOfHomeDetail> {
}
