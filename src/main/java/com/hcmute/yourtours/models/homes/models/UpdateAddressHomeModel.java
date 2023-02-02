package com.hcmute.yourtours.models.homes.models;

import com.hcmute.yourtours.libs.model.BaseData;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class UpdateAddressHomeModel extends BaseData<UUID> {
    private Integer provinceCode;
    private String address;
}
