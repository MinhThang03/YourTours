package com.hcmute.yourtours.models.surcharge_home_categories;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@SuperBuilder
public class SurchargeHomeCategoryDetail extends SurchargeHomeCategoryInfo {
}
