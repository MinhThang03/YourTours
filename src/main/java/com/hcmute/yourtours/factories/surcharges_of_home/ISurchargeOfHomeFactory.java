package com.hcmute.yourtours.factories.surcharges_of_home;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.surcharges_of_home.SurchargeOfHomeDetail;
import com.hcmute.yourtours.models.surcharges_of_home.SurchargeOfHomeInfo;
import com.hcmute.yourtours.models.surcharges_of_home.models.CreateListSurchargeHomeModel;
import com.hcmute.yourtours.models.surcharges_of_home.models.SurchargeHomeViewModel;

import java.util.List;
import java.util.UUID;

public interface ISurchargeOfHomeFactory extends IDataFactory<UUID, SurchargeOfHomeInfo, SurchargeOfHomeDetail> {
    List<SurchargeHomeViewModel> getListCategoryWithHomeId(UUID homeId);

    SuccessResponse createListSurchargeOfHome(CreateListSurchargeHomeModel request) throws InvalidException;

    BasePagingResponse<SurchargeHomeViewModel> getPageSurchargeOfHome(UUID homeId, Integer number, Integer size);

    List<SurchargeHomeViewModel> getListSurchargeOfHome(UUID homeId);

    SurchargeOfHomeDetail getByHomeIdAndCategoryId(UUID homeId, UUID categoryId) throws InvalidException;
}
