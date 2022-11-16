package com.hcmute.yourtours.factories.amenities.app;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.models.amenities.AmenityInfo;

public interface IAppAmenitiesFactory {

    BasePagingResponse<AmenityInfo> getPageAmenitiesHaveSetFilter(Integer number, Integer size) throws InvalidException;
}
