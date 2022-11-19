package com.hcmute.yourtours.factories.bed_categories.cms;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.models.bed_categories.BedCategoryInfo;
import com.hcmute.yourtours.models.bed_categories.filter.BedCategoryFilter;

public interface ICmsBedCategoryFactory {
    BasePagingResponse<BedCategoryInfo> aroundGetPageWithRoomHomeId(BedCategoryFilter filter, Integer number, Integer size) throws InvalidException;
}
