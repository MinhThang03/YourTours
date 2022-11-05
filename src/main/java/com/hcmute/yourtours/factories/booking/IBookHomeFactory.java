package com.hcmute.yourtours.factories.booking;

import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.booking.BookHomeDetail;
import com.hcmute.yourtours.models.booking.BookHomeInfo;

import java.util.UUID;

public interface IBookHomeFactory extends IDataFactory<UUID, BookHomeInfo, BookHomeDetail> {
    boolean existByUserIdAndHomeId(UUID userId, UUID homeId);
}
