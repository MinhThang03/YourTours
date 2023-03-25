package com.hcmute.yourtours.models.homes.models;

import com.hcmute.yourtours.enums.RefundPolicyEnum;
import com.hcmute.yourtours.libs.model.BaseData;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class UpdateBaseProfileHomeModel extends BaseData<UUID> {
    private String name;
    private String description;
    private String guide;
    private RefundPolicyEnum refundPolicy;
}
