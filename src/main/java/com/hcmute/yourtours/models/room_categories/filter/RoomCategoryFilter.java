package com.hcmute.yourtours.models.room_categories.filter;

import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import lombok.Data;

@Data
public class RoomCategoryFilter implements BaseFilter {
    private Boolean important;
}
