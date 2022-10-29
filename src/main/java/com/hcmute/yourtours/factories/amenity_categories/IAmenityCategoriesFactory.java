package com.hcmute.yourtours.factories.amenity_categories;

import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.amenity_categories.AmenityCategoryDetail;
import com.hcmute.yourtours.models.amenity_categories.AmenityCategoryInfo;

import java.util.UUID;

public interface IAmenityCategoriesFactory extends IDataFactory<UUID, AmenityCategoryInfo, AmenityCategoryDetail> {
    Boolean existByAmenityCategoryId(UUID amenityCategoryId);


}
