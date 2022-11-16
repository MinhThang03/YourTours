package com.hcmute.yourtours.controllers.publics.interfaces;

import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.amenities.AmenityInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface IPublicAmenitiesController {

    @GetMapping("page/set-filter")
    ResponseEntity<BaseResponse<BasePagingResponse<AmenityInfo>>> getInfoPageWithFilter(
            @RequestParam(defaultValue = "0") Integer number,
            @RequestParam(defaultValue = "20") Integer size
    );
}
