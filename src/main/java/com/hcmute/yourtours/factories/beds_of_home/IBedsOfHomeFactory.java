package com.hcmute.yourtours.factories.beds_of_home;

import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.beds_of_home.BedOfHomeDetail;
import com.hcmute.yourtours.models.beds_of_home.BedOfHomeInfo;

import java.util.UUID;

public interface IBedsOfHomeFactory extends IDataFactory<UUID, BedOfHomeInfo, BedOfHomeDetail> {
}
