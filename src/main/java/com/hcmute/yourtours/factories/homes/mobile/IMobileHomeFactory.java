package com.hcmute.yourtours.factories.homes.mobile;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.models.homes.HomeInfo;

public interface IMobileHomeFactory {

    BasePagingResponse<HomeInfo> getFavoritePage(Integer number, Integer size) throws InvalidException;
}
