package com.hcmute.yourtours.factories.amenity_categories;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.models.amenity_categories.AmenityCategoryHomeDetail;

import java.util.UUID;

public interface IAmenityCategoryHomeFactory {
    AmenityCategoryHomeDetail getDetailWithListChild(UUID categoryId, UUID homeId) throws InvalidException;
}
