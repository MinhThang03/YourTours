package com.hcmute.yourtours.models.discount_of_home;

import com.hcmute.yourtours.libs.model.BaseData;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public class DiscountOfHomeInfo extends BaseData<UUID> {

    @Min(value = 0, message = "phần trăm khuyến mãi không được phép nhỏ hơn 0")
    private Double percent;

    private Integer numberDateStay;

    private Integer numberMonthAdvance;

    @NotNull
    private UUID homeId;

    @NotNull
    private UUID categoryId;
}
