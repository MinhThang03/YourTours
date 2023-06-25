package com.hcmute.yourtours.factories.booking.cms;

import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.models.booking.models.InfoUserBookingModel;
import com.hcmute.yourtours.models.statistic.admin.filter.AdminStatisticDateFilter;

public interface ICmsBookHomeFactory {
    BasePagingResponse<InfoUserBookingModel> getStatisticInfoUserBooking(AdminStatisticDateFilter filter, Integer number, Integer size);
}
