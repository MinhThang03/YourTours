package com.hcmute.yourtours.factories.discount_of_home;


import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.discount_of_home.DiscountOfHomeDetail;
import com.hcmute.yourtours.models.discount_of_home.DiscountOfHomeInfo;
import com.hcmute.yourtours.models.discount_of_home.models.CreateListDiscountOfHomeModel;
import com.hcmute.yourtours.models.discount_of_home.models.DiscountOfHomeViewModel;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IDiscountOfHomeFactory extends IDataFactory<UUID, DiscountOfHomeInfo, DiscountOfHomeDetail> {
    List<DiscountOfHomeViewModel> getDiscountsOfHomeView(UUID homeId, LocalDate dateBooking) throws InvalidException;

    List<DiscountOfHomeViewModel> getDiscountsOfHomeView(UUID homeId) throws InvalidException;

    SuccessResponse createListDiscountOfHome(CreateListDiscountOfHomeModel request) throws InvalidException;
}
