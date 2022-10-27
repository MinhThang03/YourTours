package com.hcmute.yourtours.models.price_of_home;

import com.hcmute.yourtours.libs.model.BaseData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@SuperBuilder
public class PriceOfHomeInfo extends BaseData<UUID> {
    @NotNull
    private LocalDate date;

    @NotNull
    @Min(value = 0, message = "Giá tiền không được phép nhỏ hơn 0")
    private Double price;

    private UUID homeId;
}