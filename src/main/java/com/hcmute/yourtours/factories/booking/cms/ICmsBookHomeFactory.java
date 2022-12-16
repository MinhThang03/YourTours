package com.hcmute.yourtours.factories.booking.cms;

import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.booking.models.InfoUserBookingModel;

public interface ICmsBookHomeFactory {
    BasePagingResponse<InfoUserBookingModel> getStatisticInfoUserBooking(BaseFilter filter, Integer number, Integer size);
}
