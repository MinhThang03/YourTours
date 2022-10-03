package com.hcmute.yourtours.controllers.cms;


import com.hcmute.yourtours.controllers.cms.interfaces.ICmsRoomCategoriesController;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.model.factory.request.FactoryCreateRequest;
import com.hcmute.yourtours.libs.model.factory.request.FactoryUpdateRequest;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.libs.model.factory.response.FactoryDeleteResponse;
import com.hcmute.yourtours.libs.model.factory.response.FactoryGetResponse;
import com.hcmute.yourtours.libs.model.filter.SimpleFilter;
import com.hcmute.yourtours.models.room_categories.RoomCategoriesDetail;
import com.hcmute.yourtours.models.room_categories.RoomCategoriesInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cms/room-categories")
@Tag(name = "CMS API: ROOM CATEGORIES", description = "API admin config các loại phòng")
@Transactional
public class CmsRoomCategoriesController
        extends BaseController<UUID, RoomCategoriesInfo, RoomCategoriesDetail>
        implements ICmsRoomCategoriesController {


    protected CmsRoomCategoriesController(IDataFactory<UUID, RoomCategoriesInfo, RoomCategoriesDetail> iDataFactory, IResponseFactory iResponseFactory) {
        super(iDataFactory, iResponseFactory);
    }

    @Override
    public ResponseEntity<BaseResponse<RoomCategoriesDetail>> createModel(FactoryCreateRequest<UUID, RoomCategoriesDetail> request) {
        return super.factoryCreateModel(request);
    }

    @Override
    public ResponseEntity<BaseResponse<FactoryDeleteResponse>> deleteModelById(UUID id) {
        return super.factoryDeleteWithFilter(id, null);
    }

    @Override
    public ResponseEntity<BaseResponse<FactoryGetResponse<UUID, RoomCategoriesDetail>>> getDetailById(UUID id) {
        return super.factoryGetDetailById(id);
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<RoomCategoriesInfo>>> getInfoPageWithFilter(SimpleFilter filter, Integer number, Integer size) {
        return super.factoryGetInfoPageWithFilter(filter, number, size);
    }

    @Override
    public ResponseEntity<BaseResponse<RoomCategoriesDetail>> updateModel(FactoryUpdateRequest<UUID, RoomCategoriesDetail> request) {
        return super.factoryUpdateModel(request);
    }
}
