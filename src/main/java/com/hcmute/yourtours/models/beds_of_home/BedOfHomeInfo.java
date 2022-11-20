package com.hcmute.yourtours.models.beds_of_home;

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
public class BedOfHomeInfo extends BaseData<UUID> {
    private UUID categoryId;

    private UUID roomOfHomeId;

    @Min(value = 0, message = "số lượng giường cấu hình không được phép nhỏ hơn 1")
    @NotNull
    private Integer amount;
}
