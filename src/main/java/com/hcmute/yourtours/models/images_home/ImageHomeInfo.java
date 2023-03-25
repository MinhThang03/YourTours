package com.hcmute.yourtours.models.images_home;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ImageHomeInfo extends BaseData<UUID> {

    private String path;

    @JsonIgnore
    private UUID homeId;
}
