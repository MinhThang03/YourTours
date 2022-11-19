package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.*;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.room_categories.RoomCategoryDetail;
import com.hcmute.yourtours.models.room_categories.RoomCategoryInfo;
import com.hcmute.yourtours.models.room_categories.filter.RoomCategoryFilter;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.UUID;

public interface ICmsRoomCategoriesController extends
        ICreateModelController<UUID, RoomCategoryDetail>,
        IUpdateModelController<UUID, RoomCategoryDetail>,
        IGetDetailByIdController<UUID, RoomCategoryDetail>,
        IGetInfoPageController<UUID, RoomCategoryInfo, RoomCategoryFilter>,
        IDeleteModelByIdController<UUID> {

    @GetMapping("page/host")
    ResponseEntity<BaseResponse<BasePagingResponse<RoomCategoryInfo>>> getInfoPageWithHost(
            @ParameterObject @Valid RoomCategoryFilter filter,
            @RequestParam(defaultValue = "0") Integer number,
            @RequestParam(defaultValue = "20") Integer size
    );
}
