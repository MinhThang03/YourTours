package com.hcmute.yourtours.factories.homes;

import com.hcmute.yourtours.commands.HomesCommand;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.models.booking_guest_detail.BookingGuestDetailDetail;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.models.homes.HomeInfo;

import java.util.List;
import java.util.UUID;

public interface IHomesFactory extends IDataFactory<UUID, HomeInfo, HomeDetail> {
    HomesCommand findByHomeId(UUID homeId) throws InvalidException;

    void checkExistsByHomeId(UUID homeId) throws InvalidException;

    void checkNumberOfGuestOfHome(UUID homeId, List<BookingGuestDetailDetail> guests) throws InvalidException;

    BasePagingResponse<HomeInfo> getFilterWithProvinceName(String search, Integer number, Integer size) throws InvalidException;
}
