package com.hcmute.yourtours.models.rooms_of_home.projections;

import java.util.UUID;

public interface NumberOfRoomsProjections {
    String getRoomCategoryName();

    UUID getRoomCategoryId();

    String getNumber();
}
