package com.hcmute.yourtours.factories.province;

import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.models.province.ProvinceModel;
import com.hcmute.yourtours.models.province.ProvinceProjection;

public interface IProvinceFactory extends IDataFactory<Long, ProvinceModel, ProvinceModel> {
    BasePagingResponse<ProvinceProjection> getProvinceSortByNumberBooking(Integer number, Integer size);
}
