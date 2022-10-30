package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.ICreateModelController;
import com.hcmute.yourtours.libs.controller.IGetDetailByIdController;
import com.hcmute.yourtours.models.homes.HomeDetail;

import java.util.UUID;

public interface ICmsHomeController extends
        IGetDetailByIdController<UUID, HomeDetail>,
        ICreateModelController<UUID, HomeDetail> {
}
