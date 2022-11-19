package com.hcmute.yourtours.models.rooms_of_home;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hcmute.yourtours.models.common.NameDataModel;
import com.hcmute.yourtours.models.room_categories.RoomCategoryDetail;
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
public class RoomOfHomeInfo extends NameDataModel<UUID> {

    @JsonIgnore
    private UUID homeId;

    private UUID categoryId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private RoomCategoryDetail categoryDetail;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String descriptionOfBed;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer orderFlag;
}
