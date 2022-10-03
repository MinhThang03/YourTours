package com.hcmute.yourtours.factories.amenities;

import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.amenities.AmenitiesDetail;
import com.hcmute.yourtours.models.amenities.AmenitiesInfo;

import java.util.UUID;

public interface IAmenitiesFactory extends IDataFactory<UUID, AmenitiesInfo, AmenitiesDetail> {
}
