package com.hcmute.yourtours.models.user_evaluate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hcmute.yourtours.libs.model.BaseData;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public class UserEvaluateInfo extends BaseData<UUID> {
    @NotNull
    private UUID homeId;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID userId;

    @Min(value = 0, message = "điểm đánh giá không được thấp hơn 0")
    @Max(value = 5, message = "điểm đánh giá không được lớn hơn 5")
    private Double point;

    private String comment;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String userFullName;
}
