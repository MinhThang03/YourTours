package com.hcmute.yourtours.controllers.app.interfaces;

import com.hcmute.yourtours.libs.controller.ICreateModelController;
import com.hcmute.yourtours.libs.controller.IGetInfoPageController;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.booking.BookHomeDetail;
import com.hcmute.yourtours.models.booking.BookHomeInfo;
import com.hcmute.yourtours.models.booking.filter.AppBookingFilter;
import com.hcmute.yourtours.models.booking.request.CreateCommentRequest;
import com.hcmute.yourtours.models.booking.request.UpdateStatusBookingRequest;
import com.hcmute.yourtours.models.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.UUID;

public interface IAppBookHomeController extends
        IGetInfoPageController<UUID, BookHomeInfo, AppBookingFilter>,
        ICreateModelController<UUID, BookHomeDetail> {

    @PutMapping("/cancel")
    ResponseEntity<BaseResponse<SuccessResponse>> cancelBooking(@RequestBody @Valid UpdateStatusBookingRequest request);

    @PostMapping("/check")
    ResponseEntity<BaseResponse<SuccessResponse>> checkBooking(@RequestBody @Valid BookHomeDetail request);

    @PostMapping("/comment")
    ResponseEntity<BaseResponse<BookHomeDetail>> createComment(@RequestBody @Valid CreateCommentRequest request);
}
