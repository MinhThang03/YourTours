package com.hcmute.yourtours.factories.amenity_categories;

import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.amenity_categories.AmenityCategoriesDetail;
import com.hcmute.yourtours.models.amenity_categories.AmenityCategoriesInfo;

import java.util.UUID;

public interface IAmenityCategoriesFactory extends IDataFactory<UUID, AmenityCategoriesInfo, AmenityCategoriesDetail> {
    Boolean existByAmenityCategoryId(UUID amenityCategoryId);
}
