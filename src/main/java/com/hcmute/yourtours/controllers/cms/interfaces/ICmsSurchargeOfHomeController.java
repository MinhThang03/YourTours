package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.ICreateModelController;
import com.hcmute.yourtours.models.surcharges_of_home.SurchargeOfHomeDetail;

import java.util.UUID;

public interface ICmsSurchargeOfHomeController extends
        ICreateModelController<UUID, SurchargeOfHomeDetail> {
}
