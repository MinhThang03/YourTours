package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.IGetInfoPageController;
import com.hcmute.yourtours.models.rooms_of_home.RoomOfHomeInfo;
import com.hcmute.yourtours.models.rooms_of_home.filter.RoomOfHomeFilter;

import java.util.UUID;

public interface ICmsRoomOfHomeController extends
        IGetInfoPageController<UUID, RoomOfHomeInfo, RoomOfHomeFilter> {
}
