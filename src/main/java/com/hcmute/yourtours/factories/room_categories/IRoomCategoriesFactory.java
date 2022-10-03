package com.hcmute.yourtours.factories.room_categories;

import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.room_categories.RoomCategoriesDetail;
import com.hcmute.yourtours.models.room_categories.RoomCategoriesInfo;

import java.util.UUID;

public interface IRoomCategoriesFactory extends IDataFactory<UUID, RoomCategoriesInfo, RoomCategoriesDetail> {
}
