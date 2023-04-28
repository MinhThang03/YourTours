package com.hcmute.yourtours.factories.booking.app;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.models.booking.BookHomeDetail;
import com.hcmute.yourtours.models.booking.filter.BookingEvaluateFilter;
import com.hcmute.yourtours.models.booking.request.CreateCommentRequest;
import com.hcmute.yourtours.models.booking.response.GetPageEvaluateResponse;
import com.hcmute.yourtours.models.common.SuccessResponse;

import java.util.UUID;

public interface IAppBookHomeFactory {

    SuccessResponse checkBooking(BookHomeDetail detail) throws InvalidException;

    BookHomeDetail createComment(CreateCommentRequest request) throws InvalidException;

    BasePagingResponse<GetPageEvaluateResponse> getPageEvaluates(BookingEvaluateFilter filter, Integer number, Integer size);

    BookHomeDetail customGetDetail(UUID bookingId) throws InvalidException;
}
