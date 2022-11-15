package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.IGetInfoPageController;
import com.hcmute.yourtours.models.booking.BookHomeInfo;
import com.hcmute.yourtours.models.booking.filter.CmsBookingFilter;

import java.util.UUID;

public interface ICmsBookHomeController extends
        IGetInfoPageController<UUID, BookHomeInfo, CmsBookingFilter> {
}
