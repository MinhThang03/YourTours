package com.hcmute.yourtours.models.booking_surcharge_detail;

import com.hcmute.yourtours.libs.model.BaseData;
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
public class BookingSurchargeDetailInfo extends BaseData<UUID> {

    private UUID surchargeId;

    private UUID booking;

    private Double costOfSurcharge;

}