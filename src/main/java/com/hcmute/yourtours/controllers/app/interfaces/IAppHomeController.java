package com.hcmute.yourtours.controllers.app.interfaces;

import com.hcmute.yourtours.libs.controller.IGetInfoPageController;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.models.homes.HomeInfo;
import com.hcmute.yourtours.models.homes.filter.HomeFilter;
import com.hcmute.yourtours.models.homes.models.UserHomeDetailModel;
import com.hcmute.yourtours.models.user_evaluate.UserEvaluateDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.UUID;

public interface IAppHomeController extends
        IGetInfoPageController<UUID, HomeInfo, HomeFilter> {

    @PostMapping("/evaluates")
    ResponseEntity<BaseResponse<HomeDetail>> createEvaluate(@Valid @RequestBody UserEvaluateDetail detail);

    @GetMapping("{id}/detail")
    ResponseEntity<BaseResponse<UserHomeDetailModel>> getDetailById(@PathVariable UUID id);
}
