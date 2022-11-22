package com.hcmute.yourtours.controllers.app.interfaces;

import com.hcmute.yourtours.libs.controller.IGetInfoPageController;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.booking.BookHomeInfo;
import com.hcmute.yourtours.models.booking.request.CancelBookingRequest;
import com.hcmute.yourtours.models.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.UUID;

public interface IAppBookHomeController extends
        IGetInfoPageController<UUID, BookHomeInfo, BaseFilter> {

    @PutMapping("/cancel/booking")
    ResponseEntity<BaseResponse<SuccessResponse>> cancelBooking(@RequestBody @Valid CancelBookingRequest request);
}
