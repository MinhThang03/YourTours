package com.hcmute.yourtours.controllers.app;

import com.hcmute.yourtours.controllers.app.interfaces.IAppBookHomeController;
import com.hcmute.yourtours.factories.booking.IBookHomeFactory;
import com.hcmute.yourtours.factories.booking.app.IAppBookHomeFactory;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.exceptions.RestException;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import com.hcmute.yourtours.libs.model.factory.request.FactoryCreateRequest;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.booking.BookHomeDetail;
import com.hcmute.yourtours.models.booking.BookHomeInfo;
import com.hcmute.yourtours.models.booking.filter.AppBookingFilter;
import com.hcmute.yourtours.models.booking.request.UpdateStatusBookingRequest;
import com.hcmute.yourtours.models.common.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/app/booking")
@Tag(name = "APP API: BOOKING", description = "API khách hàng quản lý booking")
@Transactional
public class AppBookHomeController
        extends BaseController<UUID, BookHomeInfo, BookHomeDetail>
        implements IAppBookHomeController {

    private final IBookHomeFactory iBookHomeFactory;
    private final IAppBookHomeFactory iAppBookHomeFactory;

    protected AppBookHomeController
            (
                    @Qualifier("appBookHomeFactory") IBookHomeFactory iDataFactory,
                    IResponseFactory iResponseFactory,
                    @Qualifier("appBookHomeFactory") IAppBookHomeFactory iAppBookHomeFactory
            ) {
        super(iDataFactory, iResponseFactory);
        this.iBookHomeFactory = iDataFactory;
        this.iAppBookHomeFactory = iAppBookHomeFactory;
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<BookHomeInfo>>> getInfoPageWithFilter(AppBookingFilter filter, Integer number, Integer size) {
        return factoryGetInfoPageWithFilter(filter, number, size);
    }

    @Override
    @Operation(summary = "Xử lý hủy đặt phòng")
    public ResponseEntity<BaseResponse<SuccessResponse>> cancelBooking(UpdateStatusBookingRequest request) {
        LogContext.push(LogType.REQUEST, request);
        try {
            SuccessResponse response = iBookHomeFactory.handleCancelBooking(request.getBookingId());
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }

    @Override
    public ResponseEntity<BaseResponse<SuccessResponse>> checkBooking(BookHomeDetail request) {
        LogContext.push(LogType.REQUEST, request);
        try {
            SuccessResponse response = iAppBookHomeFactory.checkBooking(request);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }

    @Override
    public ResponseEntity<BaseResponse<BookHomeDetail>> createModel(FactoryCreateRequest<UUID, BookHomeDetail> request) {
        return factoryCreateModel(request);
    }
}
