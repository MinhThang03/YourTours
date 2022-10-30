package com.hcmute.yourtours.models.price_of_home.filter;

import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class PriceOfHomeFilter implements BaseFilter {

    @NotNull
    @Min(value = 1, message = "Tháng không được nhỏ hơn 1")
    @Max(value = 12, message = "Tháng không được lớn hơn 12")
    private Integer month;

    @NotNull
    private UUID homeId;
}
