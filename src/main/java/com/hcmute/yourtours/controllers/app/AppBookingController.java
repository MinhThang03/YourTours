package com.hcmute.yourtours.controllers.app;


import com.hcmute.yourtours.controllers.app.interfaces.IAppBookingController;
import com.hcmute.yourtours.factories.booking.IBookHomeFactory;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.model.factory.request.FactoryCreateRequest;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.booking.BookHomeDetail;
import com.hcmute.yourtours.models.booking.BookHomeInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/app/booking")
@Tag(name = "PUBLIC API: BOOKING", description = "API public đặt phòng")
@Transactional
public class AppBookingController
        extends BaseController<UUID, BookHomeInfo, BookHomeDetail>
        implements IAppBookingController {


    protected AppBookingController
            (
                    @Qualifier("appBookHomeFactory") IBookHomeFactory iDataFactory,
                    IResponseFactory iResponseFactory) {
        super(iDataFactory, iResponseFactory);
    }

    @Override
    public ResponseEntity<BaseResponse<BookHomeDetail>> createModel(FactoryCreateRequest<UUID, BookHomeDetail> request) {
        return factoryCreateModel(request);
    }
}
