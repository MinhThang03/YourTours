package com.hcmute.yourtours.models.guests_of_home;

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
public class GuestOfHomeInfo extends BaseData<UUID> {
    private UUID homeId;

    private UUID categoryId;

    @Min(value = 0, message = "Số lượng khách cấu hình không được phép nhỏ hơn 0")
    @NotNull
    private Integer amount;
}
