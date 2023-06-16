package com.hcmute.yourtours.models.homes;

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
public class CmsHomeInfo extends NameDataModel<UUID> {

    private String addressDetail;

    private String provinceCode;

    private Double costPerNightDefault;

    private String refundPolicy;

    private String status;

    private String ownerName;

    private UUID ownerId;

    private String provinceName;

    private String lastModifiedDate;
}