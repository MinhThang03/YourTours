package com.hcmute.yourtours.factories.home_view;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.home_view.HomeViewInfo;

import java.util.UUID;

public interface IHomeViewFactory extends IDataFactory<UUID, HomeViewInfo, HomeViewInfo> {

    HomeViewInfo increaseView(UUID homeId) throws InvalidException;

}
