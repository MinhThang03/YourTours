package com.hcmute.yourtours.controllers.mobile.interfaces;

import com.hcmute.yourtours.libs.controller.IGetInfoPageController;
import com.hcmute.yourtours.models.booking.BookHomeInfo;
import com.hcmute.yourtours.models.booking.filter.MobileBookingFilter;

import java.util.UUID;

public interface IMobileBookingController extends IGetInfoPageController<UUID, BookHomeInfo, MobileBookingFilter> {
}
