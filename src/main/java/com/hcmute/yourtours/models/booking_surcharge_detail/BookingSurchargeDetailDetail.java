package com.hcmute.yourtours.models.booking_surcharge_detail;

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
public class BookingSurchargeDetailDetail extends BookingSurchargeDetailInfo {
}
