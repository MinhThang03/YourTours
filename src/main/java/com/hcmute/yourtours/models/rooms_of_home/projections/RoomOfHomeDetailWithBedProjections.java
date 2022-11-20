package com.hcmute.yourtours.models.rooms_of_home.projections;

import java.util.UUID;

public interface RoomOfHomeDetailWithBedProjections {

    UUID getRoomHomeId();

    String getRoomName();

    Integer getNumberOfBed();

    String getNameOfBed();
}
