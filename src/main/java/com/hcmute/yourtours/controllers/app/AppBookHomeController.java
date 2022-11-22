package com.hcmute.yourtours.controllers.app;

import com.hcmute.yourtours.controllers.app.interfaces.IAppBookHomeController;
import com.hcmute.yourtours.factories.booking.IBookHomeFactory;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.exceptions.RestException;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.booking.BookHomeDetail;
import com.hcmute.yourtours.models.booking.BookHomeInfo;
import com.hcmute.yourtours.models.booking.request.CancelBookingRequest;
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

    protected AppBookHomeController
            (
                    @Qualifier("appBookHomeFactory") IBookHomeFactory iDataFactory,
                    IResponseFactory iResponseFactory
            ) {
        super(iDataFactory, iResponseFactory);
        this.iBookHomeFactory = iDataFactory;
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<BookHomeInfo>>> getInfoPageWithFilter(BaseFilter filter, Integer number, Integer size) {
        return factoryGetInfoPageWithFilter(filter, number, size);
    }

    @Override
    @Operation(summary = "Xử lý hủy đặt phòng")
    public ResponseEntity<BaseResponse<SuccessResponse>> cancelBooking(CancelBookingRequest request) {
        LogContext.push(LogType.REQUEST, request);
        try {
            SuccessResponse response = iBookHomeFactory.handleCancelBooking(request.getBookingId());
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }
}
