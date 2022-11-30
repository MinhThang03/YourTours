package com.hcmute.yourtours.controllers.app.interfaces;

import com.hcmute.yourtours.libs.controller.ICreateModelController;
import com.hcmute.yourtours.libs.controller.IGetInfoPageController;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.booking.BookHomeDetail;
import com.hcmute.yourtours.models.booking.BookHomeInfo;

import java.util.UUID;

public interface IAppBookingController extends
        ICreateModelController<UUID, BookHomeDetail>,
        IGetInfoPageController<UUID, BookHomeInfo, BaseFilter> {
}
