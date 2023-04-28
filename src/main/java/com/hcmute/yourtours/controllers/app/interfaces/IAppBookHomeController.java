package com.hcmute.yourtours.controllers.app.interfaces;

import com.hcmute.yourtours.libs.controller.ICreateModelController;
import com.hcmute.yourtours.libs.controller.IGetDetailByIdController;
import com.hcmute.yourtours.libs.controller.IGetInfoPageController;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.booking.BookHomeDetail;
import com.hcmute.yourtours.models.booking.BookHomeInfo;
import com.hcmute.yourtours.models.booking.filter.AppBookingFilter;
import com.hcmute.yourtours.models.booking.filter.BookingEvaluateFilter;
import com.hcmute.yourtours.models.booking.request.CreateCommentRequest;
import com.hcmute.yourtours.models.booking.request.UpdateStatusBookingRequest;
import com.hcmute.yourtours.models.booking.response.GetPageEvaluateResponse;
import com.hcmute.yourtours.models.common.SuccessResponse;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

public interface IAppBookHomeController extends
        IGetInfoPageController<UUID, BookHomeInfo, AppBookingFilter>,
        ICreateModelController<UUID, BookHomeDetail>,
        IGetDetailByIdController<UUID, BookHomeDetail> {

    @PutMapping("/cancel")
    ResponseEntity<BaseResponse<SuccessResponse>> cancelBooking(@RequestBody @Valid UpdateStatusBookingRequest request);

    @PostMapping("/check")
    ResponseEntity<BaseResponse<SuccessResponse>> checkBooking(@RequestBody @Valid BookHomeDetail request);

    @PostMapping("/comment")
    ResponseEntity<BaseResponse<BookHomeDetail>> createComment(@RequestBody @Valid CreateCommentRequest request);

    @GetMapping("page/evaluates")
    ResponseEntity<BaseResponse<BasePagingResponse<GetPageEvaluateResponse>>> getEvaluatePage(
            @ParameterObject @Valid BookingEvaluateFilter filter,
            @RequestParam(defaultValue = "0") Integer number,
            @RequestParam(defaultValue = "20") Integer size
    );
}
