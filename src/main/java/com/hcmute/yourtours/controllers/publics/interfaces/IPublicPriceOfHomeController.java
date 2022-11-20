package com.hcmute.yourtours.controllers.publics.interfaces;

import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.price_of_home.request.GetPriceOfHomeRequest;
import com.hcmute.yourtours.models.price_of_home.response.PriceOfHomeResponse;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;

public interface IPublicPriceOfHomeController {
    @GetMapping("")
    ResponseEntity<BaseResponse<PriceOfHomeResponse>> getPriceByHomeId(@ParameterObject @Valid GetPriceOfHomeRequest filter);
}
