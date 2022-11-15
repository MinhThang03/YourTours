package com.hcmute.yourtours.models.homes.models;

import com.hcmute.yourtours.enums.RefundPolicyEnum;
import com.hcmute.yourtours.libs.model.BaseData;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UpdateBaseProfileHomeModel extends BaseData<UUID> {
    private String name;
    private String description;
    private String guide;
    private RefundPolicyEnum refundPolicy;
}
