package com.hcmute.yourtours.controllers.app.interfaces;

import com.hcmute.yourtours.libs.controller.ICreateModelController;
import com.hcmute.yourtours.models.booking.BookHomeDetail;

import java.util.UUID;

public interface IAppBookingController extends
        ICreateModelController<UUID, BookHomeDetail> {
}
