package com.hcmute.yourtours.controllers.app.interfaces;

import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.notification.request.TickViewRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

public interface IAppNotificationController {

    @PutMapping("/tick-view")
    ResponseEntity<BaseResponse<SuccessResponse>> tickView(@Valid @RequestBody TickViewRequest request);

    @DeleteMapping("/list/delete")
    ResponseEntity<BaseResponse<SuccessResponse>> deleteList(@RequestParam(value = "all", required = false) Boolean all);


}
