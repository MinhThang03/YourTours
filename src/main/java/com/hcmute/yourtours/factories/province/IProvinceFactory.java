package com.hcmute.yourtours.factories.province;

import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.models.province.ProvinceModel;

public interface IProvinceFactory {
    BasePagingResponse<ProvinceModel> getListProvinceWithFilter(Integer number, Integer size);
}
