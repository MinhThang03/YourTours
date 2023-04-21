package com.hcmute.yourtours.models.booking.filter;

import com.hcmute.yourtours.enums.BookRoomStatusEnum;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import lombok.Data;

@Data
public class AppBookingFilter implements BaseFilter {
    BookRoomStatusEnum status;
}
