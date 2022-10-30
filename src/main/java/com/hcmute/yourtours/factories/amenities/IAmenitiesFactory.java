package com.hcmute.yourtours.factories.amenities;

import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.amenities.AmenityDetail;
import com.hcmute.yourtours.models.amenities.AmenityInfo;

import java.util.UUID;

public interface IAmenitiesFactory extends IDataFactory<UUID, AmenityInfo, AmenityDetail> {

}
