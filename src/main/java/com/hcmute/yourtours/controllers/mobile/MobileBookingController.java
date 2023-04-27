package com.hcmute.yourtours.controllers.mobile;

import com.hcmute.yourtours.controllers.mobile.interfaces.IMobileBookingController;
import com.hcmute.yourtours.factories.booking.IBookHomeFactory;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.booking.BookHomeDetail;
import com.hcmute.yourtours.models.booking.BookHomeInfo;
import com.hcmute.yourtours.models.booking.filter.MobileBookingFilter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/mobile/booking")
@Tag(name = "MOBILE API: BOOKING", description = "API mobile quản lý booking")
@Transactional
public class MobileBookingController
        extends BaseController<UUID, BookHomeInfo, BookHomeDetail>
        implements IMobileBookingController {


    protected MobileBookingController(@Qualifier("mobileBookHomeFactory") IBookHomeFactory iDataFactory,
                                      IResponseFactory iResponseFactory) {
        super(iDataFactory, iResponseFactory);
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<BookHomeInfo>>> getInfoPageWithFilter(MobileBookingFilter filter, Integer number, Integer size) {
        return factoryGetInfoPageWithFilter(filter, number, size);
    }

}
