package com.hcmute.yourtours.factories.amenities;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.amenities.AmenityDetail;
import com.hcmute.yourtours.models.amenities.AmenityInfo;

import java.util.List;
import java.util.UUID;

public interface IAmenitiesFactory extends IDataFactory<UUID, AmenityInfo, AmenityDetail> {
    List<AmenityInfo> getLimitTrueByHomeId(UUID homeId) throws InvalidException;

}
