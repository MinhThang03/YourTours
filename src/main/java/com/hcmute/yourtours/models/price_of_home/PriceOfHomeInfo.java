package com.hcmute.yourtours.models.price_of_home;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hcmute.yourtours.libs.model.BaseData;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public class PriceOfHomeInfo extends BaseData<UUID> {
    @NotNull
    private LocalDate date;

    @NotNull
    @Min(value = 0, message = "Giá tiền không được phép nhỏ hơn 0")
    private Double price;

    private UUID homeId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean isEspecially;
}
