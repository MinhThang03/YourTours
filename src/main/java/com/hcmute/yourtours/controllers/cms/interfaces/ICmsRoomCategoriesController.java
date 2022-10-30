package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.*;
import com.hcmute.yourtours.models.room_categories.RoomCategoryDetail;
import com.hcmute.yourtours.models.room_categories.RoomCategoryInfo;
import com.hcmute.yourtours.models.room_categories.filter.RoomCategoryFilter;

import java.util.UUID;

public interface ICmsRoomCategoriesController extends
        ICreateModelController<UUID, RoomCategoryDetail>,
        IUpdateModelController<UUID, RoomCategoryDetail>,
        IGetDetailByIdController<UUID, RoomCategoryDetail>,
        IGetInfoPageController<UUID, RoomCategoryInfo, RoomCategoryFilter>,
        IDeleteModelByIdController<UUID> {
}
