package com.hcmute.yourtours.models.booking_guest_detail;

import com.hcmute.yourtours.enums.GuestsCategoryEnum;
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
public class BookingGuestDetailInfo extends BaseData<UUID> {

    private GuestsCategoryEnum guestCategory;

    private Integer number;

    private UUID booking;

}
