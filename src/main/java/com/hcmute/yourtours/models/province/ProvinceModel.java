package com.hcmute.yourtours.models.province;

import com.hcmute.yourtours.libs.model.BaseData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
public class ProvinceModel extends BaseData<Long> {

    private String codeName;

    private String name;

    private String divisionType;

    private String thumbnail;

    private String enName;
}
