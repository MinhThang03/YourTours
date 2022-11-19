package com.hcmute.yourtours.models.bed_categories;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hcmute.yourtours.enums.CommonStatusEnum;
import com.hcmute.yourtours.models.common.NameDataModel;
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
@SuperBuilder(toBuilder = true)
public class BedCategoryInfo extends NameDataModel<UUID> {
    private CommonStatusEnum status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer numberOfRoom;
}
