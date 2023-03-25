package com.hcmute.yourtours.controllers.app.interfaces;

import com.hcmute.yourtours.libs.controller.IGetInfoPageController;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.models.homes.HomeInfo;
import com.hcmute.yourtours.models.homes.filter.HomeFilter;
import com.hcmute.yourtours.models.homes.filter.HomeMobileFilter;
import com.hcmute.yourtours.models.user_evaluate.UserEvaluateDetail;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.UUID;

public interface IAppHomeController extends
        IGetInfoPageController<UUID, HomeInfo, HomeFilter> {

    @PostMapping("/evaluates")
    ResponseEntity<BaseResponse<HomeDetail>> createEvaluate(@Valid @RequestBody UserEvaluateDetail detail);

    @GetMapping("page/filter")
    ResponseEntity<BaseResponse<BasePagingResponse<HomeInfo>>> getInfoPageWithFullFilter(
            @ParameterObject @Valid HomeMobileFilter filter,
            @RequestParam(defaultValue = "0") Integer number,
            @RequestParam(defaultValue = "20") Integer size
    );

}
