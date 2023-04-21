package com.hcmute.yourtours.controllers.publics.interfaces;

import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.geo_ip_location.GeoIPLocation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface IPublicLocationController {

    @GetMapping("")
    ResponseEntity<BaseResponse<GeoIPLocation>> getCurrentLocation();

}
