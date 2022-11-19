package com.hcmute.yourtours.models.rooms_of_home.models;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CreateListRoomOfHomeModel {
    private UUID homeId;
    private List<RoomOfHomeCreateModel> listCreate;
}
