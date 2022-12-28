package com.hcmute.yourtours.factories.booking.app;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.models.booking.BookHomeDetail;
import com.hcmute.yourtours.models.common.SuccessResponse;

public interface IAppBookHomeFactory {

    SuccessResponse checkBooking(BookHomeDetail detail) throws InvalidException;
}
