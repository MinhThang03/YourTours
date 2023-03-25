package com.hcmute.yourtours.models.surcharge_home_categories;

import com.hcmute.yourtours.enums.CommonStatusEnum;
import com.hcmute.yourtours.models.common.NameDataModel;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public class SurchargeHomeCategoryInfo extends NameDataModel<UUID> {
    private CommonStatusEnum status;
}
