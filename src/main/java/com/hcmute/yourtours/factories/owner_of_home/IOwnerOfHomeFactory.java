package com.hcmute.yourtours.factories.owner_of_home;

import com.hcmute.yourtours.entities.OwnerOfHome;
import com.hcmute.yourtours.enums.UserStatusEnum;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.models.owner_of_home.OwnerOfHomeDetail;
import com.hcmute.yourtours.models.owner_of_home.OwnerOfHomeInfo;
import com.hcmute.yourtours.models.owner_of_home.models.StatisticInfoOwnerModel;
import com.hcmute.yourtours.models.statistic.admin.filter.AdminStatisticDateFilter;

import java.util.UUID;

public interface IOwnerOfHomeFactory extends IDataFactory<UUID, OwnerOfHomeInfo, OwnerOfHomeDetail> {
    boolean existByOwnerIdAndHomeId(UUID ownerId, UUID homeId);

    String getMainOwnerOfHome(UUID homeId);

    BasePagingResponse<StatisticInfoOwnerModel> getStatisticInfoOwner(AdminStatisticDateFilter filter, Integer number, Integer size);

    OwnerOfHome getMainOwnerByHomeId(UUID homeId) throws InvalidException;

    UserStatusEnum getStatusOfOwnerHome(UUID homeId);
}
