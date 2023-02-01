package com.hcmute.yourtours.models.rules_of_home;

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
public class RuleOfHomeInfo extends BaseData<UUID> {
    private Boolean isHave;

    private UUID ruleHomeId;

    private UUID homeId;
}
