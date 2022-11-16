package com.hcmute.yourtours.controllers.publics;

import com.hcmute.yourtours.controllers.publics.interfaces.IPublicAmenitiesController;
import com.hcmute.yourtours.factories.amenities.IAmenitiesFactory;
import com.hcmute.yourtours.factories.amenities.app.IAppAmenitiesFactory;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.exceptions.RestException;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.amenities.AmenityDetail;
import com.hcmute.yourtours.models.amenities.AmenityInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/public/amenities")
@Tag(name = "PUBLIC API: AMENITIES", description = "API public tiện ích")
@Transactional
public class PublicAmenitiesController
        extends BaseController<UUID, AmenityInfo, AmenityDetail>
        implements IPublicAmenitiesController {

    private final IAppAmenitiesFactory iAppAmenitiesFactory;

    protected PublicAmenitiesController
            (
                    @Qualifier("appAmenitiesFactory") IAmenitiesFactory iDataFactory,
                    IResponseFactory iResponseFactory,
                    IAppAmenitiesFactory iAppAmenitiesFactory
            ) {
        super(iDataFactory, iResponseFactory);
        this.iAppAmenitiesFactory = iAppAmenitiesFactory;
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<AmenityInfo>>> getInfoPageWithFilter(Integer number, Integer size) {
        try {
            BasePagingResponse<AmenityInfo> response = iAppAmenitiesFactory.getPageAmenitiesHaveSetFilter(number, size);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }
}
