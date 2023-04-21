package com.hcmute.yourtours.controllers.publics;

import com.hcmute.yourtours.controllers.publics.interfaces.IPublicLocationController;
import com.hcmute.yourtours.factories.geo_ip_location.IGeoIPLocationFactory;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.geo_ip_location.GeoIPLocation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public/location")
@Tag(name = "PUBLIC API: LOCATION", description = "API lấy thông tin vị trị")
@Transactional
public class PublicLocationController implements IPublicLocationController {

    private final IGeoIPLocationFactory iGeoIPLocationFactory;
    private final IResponseFactory iResponseFactory;

    public PublicLocationController(IGeoIPLocationFactory iGeoIPLocationFactory, IResponseFactory iResponseFactory) {
        this.iGeoIPLocationFactory = iGeoIPLocationFactory;
        this.iResponseFactory = iResponseFactory;
    }


    @Override
    @Operation(summary = "Lấy thông tin vị trí hiện tại")
    public ResponseEntity<BaseResponse<GeoIPLocation>> getCurrentLocation() {
        GeoIPLocation response = iGeoIPLocationFactory.getLocationByCurrentIp();
        LogContext.push(LogType.RESPONSE, response);
        return iResponseFactory.success(response);
    }
}
