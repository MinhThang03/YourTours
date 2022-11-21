package com.hcmute.yourtours.factories.booking_surcharge_detail;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.booking_surcharge_detail.BookingSurchargeDetailDetail;
import com.hcmute.yourtours.models.booking_surcharge_detail.BookingSurchargeDetailInfo;

import java.util.List;
import java.util.UUID;

public interface IBookingSurchargeDetailFactory extends IDataFactory<UUID, BookingSurchargeDetailInfo, BookingSurchargeDetailDetail> {

    void createListModel(UUID bookingId, List<BookingSurchargeDetailDetail> listDetail) throws InvalidException;

}
