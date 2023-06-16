package com.hcmute.yourtours.models.homes.projections;

import com.hcmute.yourtours.enums.CommonStatusEnum;
import com.hcmute.yourtours.enums.RefundPolicyEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public interface CmsHomeInfoProjection {
    UUID getId();

    String getName();

    String getDescription();

    String getAddressDetail();

    String getProvinceCode();

    Double getCostPerNightDefault();

    RefundPolicyEnum getRefundPolicy();

    CommonStatusEnum getStatus();

    String getOwnerName();

    UUID getOwnerId();

    String getProvinceName();

    LocalDateTime getLastModifiedDate();

    boolean getDeleted();
}
