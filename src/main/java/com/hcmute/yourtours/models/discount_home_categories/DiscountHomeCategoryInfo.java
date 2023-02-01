package com.hcmute.yourtours.models.discount_home_categories;

import com.hcmute.yourtours.enums.CommonStatusEnum;
import com.hcmute.yourtours.enums.DiscountHomeEnum;
import com.hcmute.yourtours.models.common.NameDataModel;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public class DiscountHomeCategoryInfo extends NameDataModel<UUID> {

    @NotNull
    private DiscountHomeEnum type;

    private Integer numDateDefault;

    private CommonStatusEnum status;
}
