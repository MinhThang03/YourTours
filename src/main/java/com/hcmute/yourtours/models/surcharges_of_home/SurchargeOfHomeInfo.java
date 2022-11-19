package com.hcmute.yourtours.models.surcharges_of_home;

import com.hcmute.yourtours.libs.model.BaseData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@SuperBuilder
public class SurchargeOfHomeInfo extends BaseData<UUID> {
    @Min(value = 0, message = "Giá trị phụ phí không được phép nhỏ hơn 0")
    private Double cost;

    @NotNull
    private UUID categoryId;

    @NotNull
    private UUID homeId;
}
