package com.hcmute.yourtours.controllers.cms;

import com.hcmute.yourtours.controllers.cms.interfaces.ICmsBookHomeController;
import com.hcmute.yourtours.factories.booking.IBookHomeFactory;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.exceptions.RestException;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.booking.BookHomeDetail;
import com.hcmute.yourtours.models.booking.BookHomeInfo;
import com.hcmute.yourtours.models.booking.filter.CmsBookingFilter;
import com.hcmute.yourtours.models.booking.request.UpdateStatusBookingRequest;
import com.hcmute.yourtours.models.common.SuccessResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cms/booking")
@Tag(name = "CMS API: BOOKING", description = "API admin quản lý việc đặt phòng")
@Transactional
public class CmsBookHomeController
        extends BaseController<UUID, BookHomeInfo, BookHomeDetail>
        implements ICmsBookHomeController {

    private final IBookHomeFactory iBookHomeFactory;

    protected CmsBookHomeController
            (
                    @Qualifier("cmsBookHomeFactory") IBookHomeFactory iDataFactory,
                    IResponseFactory iResponseFactory
            ) {
        super(iDataFactory, iResponseFactory);
        this.iBookHomeFactory = iDataFactory;
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<BookHomeInfo>>> getInfoPageWithFilter(CmsBookingFilter filter, Integer number, Integer size) {
        return factoryGetInfoPageWithFilter(filter, number, size);
    }

    @Override
    public ResponseEntity<BaseResponse<SuccessResponse>> checkInBooking(UpdateStatusBookingRequest request) {
        try {
            LogContext.push(LogType.REQUEST, request);
            SuccessResponse response = iBookHomeFactory.handleCheckInBooking(request.getBookingId());
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }

    @Override
    public ResponseEntity<BaseResponse<SuccessResponse>> checkOutBooking(UpdateStatusBookingRequest request) {
        try {
            LogContext.push(LogType.REQUEST, request);
            SuccessResponse response = iBookHomeFactory.handleCheckOutBooking(request.getBookingId());
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }
}
