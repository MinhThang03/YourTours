package com.hcmute.yourtours.controllers.app.interfaces;

import com.hcmute.yourtours.libs.controller.ICreateModelController;
import com.hcmute.yourtours.libs.controller.IDeleteModelByIdController;
import com.hcmute.yourtours.libs.controller.IGetInfoPageController;
import com.hcmute.yourtours.libs.controller.IUpdateModelController;
import com.hcmute.yourtours.models.user_evaluate.UserEvaluateDetail;
import com.hcmute.yourtours.models.user_evaluate.UserEvaluateInfo;
import com.hcmute.yourtours.models.user_evaluate.filter.EvaluateFilter;

import java.util.UUID;

public interface IAppUserEvaluateController extends
        IGetInfoPageController<UUID, UserEvaluateInfo, EvaluateFilter>,
        ICreateModelController<UUID, UserEvaluateDetail>,
        IUpdateModelController<UUID, UserEvaluateDetail>,
        IDeleteModelByIdController<UUID> {
}
