package com.hcmute.yourtours.factories.homes;

import com.hcmute.yourtours.entities.Homes;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.models.booking_guest_detail.BookingGuestDetailDetail;
import com.hcmute.yourtours.models.homes.CmsHomeInfo;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.models.homes.HomeInfo;
import com.hcmute.yourtours.models.homes.projections.GetOwnerNameAndHomeNameProjection;
import com.hcmute.yourtours.models.homes.requests.UpdateHomeStatusRequest;
import com.hcmute.yourtours.models.homes.responses.UpdateHomeStatusResponse;

import java.util.List;
import java.util.UUID;

public interface IHomesFactory extends IDataFactory<UUID, HomeInfo, HomeDetail> {
    Homes findByHomeId(UUID homeId) throws InvalidException;

    void checkExistsByHomeId(UUID homeId) throws InvalidException;

    void checkNumberOfGuestOfHome(UUID homeId, List<BookingGuestDetailDetail> guests) throws InvalidException;

    BasePagingResponse<HomeInfo> getFilterWithProvinceName(String search, Integer number, Integer size) throws InvalidException;

    BasePagingResponse<CmsHomeInfo> getPageWithRoleAdmin(String keyword, Integer number, Integer size) throws InvalidException;

    GetOwnerNameAndHomeNameProjection getOwnerNameAndHomeNameProjection(UUID homeId);

    UpdateHomeStatusResponse updateHomeStatus(UpdateHomeStatusRequest request) throws InvalidException;
}
