package com.hcmute.yourtours.factories.guests_of_home;

import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.guests_of_home.GuestOfHomeDetail;
import com.hcmute.yourtours.models.guests_of_home.GuestOfHomeInfo;

import java.util.UUID;

public interface IGuestsOfHomeFactory extends IDataFactory<UUID, GuestOfHomeInfo, GuestOfHomeDetail> {
}
