package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.ICreateModelController;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.surcharges_of_home.SurchargeOfHomeDetail;
import com.hcmute.yourtours.models.surcharges_of_home.models.CreateListSurchargeHomeModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.UUID;

public interface ICmsSurchargeOfHomeController extends
        ICreateModelController<UUID, SurchargeOfHomeDetail> {

    @PostMapping(value = "create/list")
    ResponseEntity<BaseResponse<SuccessResponse>> createModelWithList(@RequestBody @Valid CreateListSurchargeHomeModel request);
}
