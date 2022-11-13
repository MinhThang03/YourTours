package com.hcmute.yourtours.factories.amenity_categories;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.models.amenity_categories.AmenityCategoryHomeDetail;
import com.hcmute.yourtours.models.amenity_categories.filter.AmenityCategoryFilter;

import java.util.UUID;

public interface IAmenityCategoryHomeFactory {
    AmenityCategoryHomeDetail getDetailWithListChild(UUID categoryId, UUID homeId) throws InvalidException;

    BasePagingResponse<AmenityCategoryHomeDetail> getPageWithChildren(AmenityCategoryFilter filter, Integer number, Integer size) throws InvalidException;
}
