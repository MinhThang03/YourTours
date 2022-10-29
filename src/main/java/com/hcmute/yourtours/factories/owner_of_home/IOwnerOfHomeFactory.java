package com.hcmute.yourtours.factories.owner_of_home;

import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.owner_of_home.OwnerOfHomeDetail;
import com.hcmute.yourtours.models.owner_of_home.OwnerOfHomeInfo;

import java.util.UUID;

public interface IOwnerOfHomeFactory extends IDataFactory<UUID, OwnerOfHomeInfo, OwnerOfHomeDetail> {
    boolean existByOwnerIdAndHomeId(UUID ownerId, UUID homeId);
}
