package com.hcmute.yourtours.models.rooms_of_home.models;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
public class NumberOfRoomsModel implements Serializable {
    private String roomCategoryName;
    private UUID roomCategoryId;
    private String number;
}
