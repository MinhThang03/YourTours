package com.hcmute.yourtours.models.room_categories.filter;

import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import lombok.Data;

import java.util.UUID;

@Data
public class RoomCategoryFilter implements BaseFilter {
    private Boolean important;
    private UUID homeId;
}
