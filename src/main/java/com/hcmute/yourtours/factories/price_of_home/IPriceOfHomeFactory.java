package com.hcmute.yourtours.factories.price_of_home;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.price_of_home.PriceOfHomeDetail;
import com.hcmute.yourtours.models.price_of_home.PriceOfHomeInfo;
import com.hcmute.yourtours.models.price_of_home.filter.PriceOfHomeFilter;
import com.hcmute.yourtours.models.price_of_home.request.GetPriceOfHomeRequest;
import com.hcmute.yourtours.models.price_of_home.request.PriceOfHomeCreateRequest;
import com.hcmute.yourtours.models.price_of_home.response.PriceOfHomeResponse;
import com.hcmute.yourtours.models.price_of_home.response.PriceOfHomeWithMonthResponse;

import java.util.UUID;

public interface IPriceOfHomeFactory extends IDataFactory<UUID, PriceOfHomeInfo, PriceOfHomeDetail> {
    SuccessResponse createWithHomeId(PriceOfHomeCreateRequest request) throws InvalidException;

    PriceOfHomeWithMonthResponse getByHomeIdAndMonth(PriceOfHomeFilter filter) throws InvalidException;

    PriceOfHomeResponse getCostBetweenDay(GetPriceOfHomeRequest request) throws InvalidException;
}
