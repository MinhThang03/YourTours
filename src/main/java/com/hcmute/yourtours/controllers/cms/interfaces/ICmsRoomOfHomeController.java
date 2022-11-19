package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.IDeleteModelByIdController;
import com.hcmute.yourtours.libs.controller.IGetInfoPageController;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.rooms_of_home.RoomOfHomeInfo;
import com.hcmute.yourtours.models.rooms_of_home.filter.RoomOfHomeFilter;
import com.hcmute.yourtours.models.rooms_of_home.models.CreateListRoomOfHomeModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.UUID;

public interface ICmsRoomOfHomeController extends
        IGetInfoPageController<UUID, RoomOfHomeInfo, RoomOfHomeFilter>,
        IDeleteModelByIdController<UUID> {

    @PostMapping(value = "create/list")
    ResponseEntity<BaseResponse<SuccessResponse>> createModelWithList(@RequestBody @Valid CreateListRoomOfHomeModel request);
}
