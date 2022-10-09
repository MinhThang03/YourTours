package com.hcmute.yourtours.factories.homes;

import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.models.homes.HomeInfo;

import java.util.UUID;

public interface IHomesFactory extends IDataFactory<UUID, HomeInfo, HomeDetail> {
}
