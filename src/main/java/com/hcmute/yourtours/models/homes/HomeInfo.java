package com.hcmute.yourtours.models.homes;

import com.hcmute.yourtours.enums.CommonStatusEnum;
import com.hcmute.yourtours.enums.RefundPolicyEnum;
import com.hcmute.yourtours.models.common.NameDataModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@SuperBuilder
public class HomeInfo extends NameDataModel<UUID> {

    private String wifi;

    private String passWifi;

    private String ruleOthers;

    private LocalTime timeCheckInStart;

    private LocalTime timeCheckInEnd;

    private LocalTime timeCheckOut;

    private String guide;

    private String addressDetail;

    private Integer provinceCode;

    private Integer rank;

    @Min(value = 0, message = "Giá tiền không được phép nhỏ hơn 0")
    @NotNull
    private Double costPerNightDefault;

    private RefundPolicyEnum refundPolicy;

    private CommonStatusEnum status;

    @Min(value = 0, message = "Số lượng khách không được phép nhỏ hơn 0")
    private Integer numberOfGuests;
}
