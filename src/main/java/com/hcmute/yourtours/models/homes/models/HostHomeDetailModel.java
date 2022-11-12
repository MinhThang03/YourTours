package com.hcmute.yourtours.models.homes.models;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.hcmute.yourtours.models.amenities.AmenityInfo;
import com.hcmute.yourtours.models.discount_of_home.models.DiscountOfHomeViewModel;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.models.rooms_of_home.models.NumberOfRoomsModel;
import com.hcmute.yourtours.models.surcharges_of_home.models.SurchargeHomeViewModel;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class HostHomeDetailModel implements Serializable {
    @JsonUnwrapped
    private HomeDetail homeDetail;
    private List<NumberOfRoomsModel> numberOfRooms;
    private List<SurchargeHomeViewModel> surcharges;
    private List<DiscountOfHomeViewModel> discounts;
    private List<AmenityInfo> amenities;
}
