package com.hcmute.yourtours.controllers.publics.interfaces;

import com.hcmute.yourtours.libs.controller.ICreateModelController;
import com.hcmute.yourtours.models.booking.BookHomeDetail;

import java.util.UUID;

public interface IPublicBookingController extends
        ICreateModelController<UUID, BookHomeDetail> {
}
