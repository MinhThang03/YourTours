package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.price_of_home.filter.PriceOfHomeFilter;
import com.hcmute.yourtours.models.price_of_home.request.PriceOfHomeCreateRequest;
import com.hcmute.yourtours.models.price_of_home.response.PriceOfHomeWithMonthResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface ICmsPriceOfHomeController {
    @PostMapping(value = "create")
    @Operation(summary = "Custome giá theo ngày của một nhà")
    ResponseEntity<BaseResponse<SuccessResponse>> createModel(@RequestBody @Valid PriceOfHomeCreateRequest request);

    @GetMapping("/months")
    @Operation(summary = "Lấy thông tin giá của một nhà trong 1 tháng")
    ResponseEntity<BaseResponse<PriceOfHomeWithMonthResponse>> getWithMonth(
            @ParameterObject @Valid PriceOfHomeFilter filter
    );
}
