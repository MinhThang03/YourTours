package com.hcmute.yourtours.models.owner_of_home;

import com.hcmute.yourtours.libs.model.BaseData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@SuperBuilder
public class OwnerOfHomeInfo extends BaseData<UUID> {
    private Boolean isMainOwner;

    private String email;

    private UUID homeId;

    private UUID userId;
}
