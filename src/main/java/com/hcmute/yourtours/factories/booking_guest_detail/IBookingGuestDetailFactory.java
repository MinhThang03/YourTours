package com.hcmute.yourtours.factories.booking_guest_detail;

import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.booking_guest_detail.BookingGuestDetailDetail;
import com.hcmute.yourtours.models.booking_guest_detail.BookingGuestDetailInfo;

import java.util.UUID;

public interface IBookingGuestDetailFactory extends IDataFactory<UUID, BookingGuestDetailInfo, BookingGuestDetailDetail> {
}
