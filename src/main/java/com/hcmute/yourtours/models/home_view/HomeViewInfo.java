package com.hcmute.yourtours.models.home_view;

import com.hcmute.yourtours.libs.model.BaseData;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public class HomeViewInfo extends BaseData<UUID> {

    @NotNull
    private UUID homeId;

    @NotNull
    private Long view;
}
