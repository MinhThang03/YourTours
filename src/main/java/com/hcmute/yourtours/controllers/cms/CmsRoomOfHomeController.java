package com.hcmute.yourtours.controllers.cms;

import com.hcmute.yourtours.controllers.cms.interfaces.ICmsRoomOfHomeController;
import com.hcmute.yourtours.factories.rooms_of_home.IRoomsOfHomeFactory;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.exceptions.RestException;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.libs.model.factory.response.FactoryDeleteResponse;
import com.hcmute.yourtours.models.rooms_of_home.RoomOfHomeDetail;
import com.hcmute.yourtours.models.rooms_of_home.RoomOfHomeInfo;
import com.hcmute.yourtours.models.rooms_of_home.filter.RoomOfHomeFilter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cms/room-of-home")
@Tag(name = "CMS API: ROOM OF HOME", description = "API admin config các phòng cho ngôi nhà")
@Transactional
public class CmsRoomOfHomeController
        extends BaseController<UUID, RoomOfHomeInfo, RoomOfHomeDetail>
        implements ICmsRoomOfHomeController {

    private final IRoomsOfHomeFactory iRoomsOfHomeFactory;

    protected CmsRoomOfHomeController
            (
                    IRoomsOfHomeFactory iDataFactory,
                    IResponseFactory iResponseFactory
            ) {
        super(iDataFactory, iResponseFactory);
        this.iRoomsOfHomeFactory = iDataFactory;
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<RoomOfHomeInfo>>> getInfoPageWithFilter(RoomOfHomeFilter filter, Integer number, Integer size) {
        try {
            BasePagingResponse<RoomOfHomeInfo> response = iRoomsOfHomeFactory.getPageRoomOfHomeOfHost(filter, number, size);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }

    @Override
    public ResponseEntity<BaseResponse<FactoryDeleteResponse>> deleteModelById(UUID id) {
        return factoryDeleteWithFilter(id, null);
    }
}
