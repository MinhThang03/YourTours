package com.hcmute.yourtours.controllers.cms;


import com.hcmute.yourtours.controllers.cms.interfaces.ICmsRoomCategoriesController;
import com.hcmute.yourtours.factories.room_categories.IRoomCategoriesFactory;
import com.hcmute.yourtours.factories.room_categories.cms.ICmsRoomCategoriesFactory;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.exceptions.RestException;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import com.hcmute.yourtours.libs.model.factory.request.FactoryCreateRequest;
import com.hcmute.yourtours.libs.model.factory.request.FactoryUpdateRequest;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.libs.model.factory.response.FactoryDeleteResponse;
import com.hcmute.yourtours.libs.model.factory.response.FactoryGetResponse;
import com.hcmute.yourtours.models.room_categories.RoomCategoryDetail;
import com.hcmute.yourtours.models.room_categories.RoomCategoryInfo;
import com.hcmute.yourtours.models.room_categories.filter.RoomCategoryFilter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cms/room-categories")
@Tag(name = "CMS API: ROOM CATEGORIES", description = "API admin config các loại phòng")
@Transactional
public class CmsRoomCategoriesController
        extends BaseController<UUID, RoomCategoryInfo, RoomCategoryDetail>
        implements ICmsRoomCategoriesController {

    private final ICmsRoomCategoriesFactory iCmsRoomCategoriesFactory;

    protected CmsRoomCategoriesController
            (
                    @Qualifier("roomCategoriesFactory") IRoomCategoriesFactory iDataFactory,
                    IResponseFactory iResponseFactory,
                    ICmsRoomCategoriesFactory iCmsRoomCategoriesFactory) {
        super(iDataFactory, iResponseFactory);
        this.iCmsRoomCategoriesFactory = iCmsRoomCategoriesFactory;
    }

    @Override
    public ResponseEntity<BaseResponse<RoomCategoryDetail>> createModel(FactoryCreateRequest<UUID, RoomCategoryDetail> request) {
        return super.factoryCreateModel(request);
    }

    @Override
    public ResponseEntity<BaseResponse<FactoryDeleteResponse>> deleteModelById(UUID id) {
        return super.factoryDeleteWithFilter(id, null);
    }

    @Override
    public ResponseEntity<BaseResponse<FactoryGetResponse<UUID, RoomCategoryDetail>>> getDetailById(UUID id) {
        return super.factoryGetDetailById(id);
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<RoomCategoryInfo>>> getInfoPageWithFilter(RoomCategoryFilter filter, Integer number, Integer size) {
        return super.factoryGetInfoPageWithFilter(filter, number, size);
    }

    @Override
    public ResponseEntity<BaseResponse<RoomCategoryDetail>> updateModel(FactoryUpdateRequest<UUID, RoomCategoryDetail> request) {
        return super.factoryUpdateModel(request);
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<RoomCategoryInfo>>> getInfoPageWithHost(@Valid RoomCategoryFilter filter, Integer number, Integer size) {
        try {
            BasePagingResponse<RoomCategoryInfo> response = iCmsRoomCategoriesFactory.getPageRoomInHome(filter, number, size);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }
}
