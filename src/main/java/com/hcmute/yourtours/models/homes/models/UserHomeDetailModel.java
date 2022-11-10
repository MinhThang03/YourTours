package com.hcmute.yourtours.models.homes.models;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.models.rooms_of_home.RoomOfHomeDetail;
import com.hcmute.yourtours.models.user_evaluate.UserEvaluateInfo;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class UserHomeDetailModel implements Serializable {
    @JsonUnwrapped
    private HomeDetail homeDetail;
    private BasePagingResponse<UserEvaluateInfo> evaluates;
    private List<RoomOfHomeDetail> rooms;
    private Double userRate;
    private boolean isBooked;
    private boolean isFavorite;
    private List<LocalDate> dateIsBooked;
}