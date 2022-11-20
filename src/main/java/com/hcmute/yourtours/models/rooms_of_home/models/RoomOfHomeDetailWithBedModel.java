package com.hcmute.yourtours.models.rooms_of_home.models;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
public class RoomOfHomeDetailWithBedModel implements Serializable {
    private UUID roomHomeId;

    private String roomName;

    private Integer numberOfBed;

    private String nameOfBed;
}
