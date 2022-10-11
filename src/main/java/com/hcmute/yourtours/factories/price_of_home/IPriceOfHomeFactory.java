package com.hcmute.yourtours.factories.price_of_home;

import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.price_of_home.PriceOfHomeDetail;
import com.hcmute.yourtours.models.price_of_home.PriceOfHomeInfo;

import java.util.UUID;

public interface IPriceOfHomeFactory extends IDataFactory<UUID, PriceOfHomeInfo, PriceOfHomeDetail> {
}
