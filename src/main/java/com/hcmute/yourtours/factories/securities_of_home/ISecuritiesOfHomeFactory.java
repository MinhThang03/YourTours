package com.hcmute.yourtours.factories.securities_of_home;

import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.securities_of_home.SecurityOfHomeDetail;
import com.hcmute.yourtours.models.securities_of_home.SecurityOfHomeInfo;

import java.util.UUID;

public interface ISecuritiesOfHomeFactory extends IDataFactory<UUID, SecurityOfHomeInfo, SecurityOfHomeDetail> {
}
