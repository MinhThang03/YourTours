package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.IGetInfoPageController;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.booking.BookHomeInfo;
import com.hcmute.yourtours.models.booking.filter.CmsBookingFilter;
import com.hcmute.yourtours.models.booking.request.UpdateStatusBookingRequest;
import com.hcmute.yourtours.models.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.UUID;

public interface ICmsBookHomeController extends
        IGetInfoPageController<UUID, BookHomeInfo, CmsBookingFilter> {

    @PutMapping("/check-in")
    ResponseEntity<BaseResponse<SuccessResponse>> checkInBooking(@RequestBody @Valid UpdateStatusBookingRequest request);

    @PutMapping("/check-out")
    ResponseEntity<BaseResponse<SuccessResponse>> checkOutBooking(@RequestBody @Valid UpdateStatusBookingRequest request);
}
