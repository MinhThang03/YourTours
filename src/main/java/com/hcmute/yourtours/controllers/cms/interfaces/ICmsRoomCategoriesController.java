package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.*;
import com.hcmute.yourtours.libs.model.filter.SimpleFilter;
import com.hcmute.yourtours.models.room_categories.RoomCategoriesDetail;
import com.hcmute.yourtours.models.room_categories.RoomCategoriesInfo;

import java.util.UUID;

public interface ICmsRoomCategoriesController extends
        ICreateModelController<UUID, RoomCategoriesDetail>,
        IUpdateModelController<UUID, RoomCategoriesDetail>,
        IGetDetailByIdController<UUID, RoomCategoriesDetail>,
        IGetInfoPageController<UUID, RoomCategoriesInfo, SimpleFilter>,
        IDeleteModelByIdController<UUID> {
}
