package com.hcmute.yourtours.factories.amenities_of_home;

import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.amenities_of_home.AmenityOfHomeDetail;
import com.hcmute.yourtours.models.amenities_of_home.AmenityOfHomeInfo;
import com.hcmute.yourtours.models.amenities_of_home.projection.AmenityOfHomeModel;

import java.util.List;
import java.util.UUID;

public interface IAmenitiesOfHomeFactory extends IDataFactory<UUID, AmenityOfHomeInfo, AmenityOfHomeDetail> {

    List<AmenityOfHomeModel> getAllByCategoryIdAndHomeId(UUID categoryId, UUID homeId);
}
