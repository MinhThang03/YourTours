package com.hcmute.yourtours.controllers.publics.interfaces;

import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.surcharges_of_home.models.SurchargeHomeViewModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

public interface IPublicSurchargeController {

    @GetMapping("page")
    ResponseEntity<BaseResponse<BasePagingResponse<SurchargeHomeViewModel>>> getInfoPageWithHomeId(
            @RequestParam UUID homeId,
            @RequestParam(defaultValue = "0") Integer number,
            @RequestParam(defaultValue = "20") Integer size
    );
}
