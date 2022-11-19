package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.beds_of_home.models.CreateListBedOfHomeDetail;
import com.hcmute.yourtours.models.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface ICmsBedOfHomeController {

    @PostMapping(value = "create/list")
    ResponseEntity<BaseResponse<SuccessResponse>> createModelWithList(@RequestBody @Valid CreateListBedOfHomeDetail request);
}
