package com.hcmute.yourtours.models.homes.models;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.models.amenities.AmenityInfo;
import com.hcmute.yourtours.models.discount_of_home.models.DiscountOfHomeViewModel;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.models.rooms_of_home.models.RoomOfHomeDetailWithBedModel;
import com.hcmute.yourtours.models.surcharges_of_home.models.SurchargeHomeViewModel;
import com.hcmute.yourtours.models.user_evaluate.UserEvaluateInfo;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class UserHomeDetailModel implements Serializable {
    @JsonUnwrapped
    private HomeDetail homeDetail;
    private BasePagingResponse<UserEvaluateInfo> evaluates;
    private List<RoomOfHomeDetailWithBedModel> rooms;
    private Double userRate;
    private boolean isBooked;
    private List<String> dateIsBooked;
    private String ownerName;
    private List<AmenityInfo> amenitiesView;
    private String descriptionHomeDetail;
    private List<SurchargeHomeViewModel> surcharges;
    private Double totalCostBooking;
    private List<DiscountOfHomeViewModel> discounts;
}
