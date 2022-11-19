package com.hcmute.yourtours.models.rooms_of_home.filter;

import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import lombok.Data;

import java.util.UUID;

@Data
public class RoomOfHomeFilter implements BaseFilter {
    private UUID homeId;
}
