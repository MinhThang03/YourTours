package com.hcmute.yourtours.factories.room_categories;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.room_categories.RoomCategoryDetail;
import com.hcmute.yourtours.models.room_categories.RoomCategoryInfo;

import java.util.UUID;

public interface IRoomCategoriesFactory extends IDataFactory<UUID, RoomCategoryInfo, RoomCategoryDetail> {
    void checkExistByRoomCategoryId(UUID roomCategoryId) throws InvalidException;
}
