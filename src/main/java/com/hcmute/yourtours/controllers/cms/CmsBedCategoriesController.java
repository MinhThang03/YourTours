package com.hcmute.yourtours.controllers.cms;


import com.hcmute.yourtours.controllers.cms.interfaces.ICmsBedCategoriesController;
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
import com.hcmute.yourtours.models.bed_categories.BedCategoriesDetail;
import com.hcmute.yourtours.models.bed_categories.BedCategoriesInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cms/bed-categories")
@Tag(name = "CMS API: BED CATEGORIES", description = "API admin config các loại giường")
@Transactional
public class CmsBedCategoriesController
        extends BaseController<UUID, BedCategoriesInfo, BedCategoriesDetail>
        implements ICmsBedCategoriesController {

    protected CmsBedCategoriesController(IDataFactory<UUID, BedCategoriesInfo, BedCategoriesDetail> iDataFactory, IResponseFactory iResponseFactory) {
        super(iDataFactory, iResponseFactory);
    }

    @Override
    public ResponseEntity<BaseResponse<BedCategoriesDetail>> createModel(FactoryCreateRequest<UUID, BedCategoriesDetail> request) {
        return super.factoryCreateModel(request);
    }

    @Override
    public ResponseEntity<BaseResponse<FactoryDeleteResponse>> deleteModelById(UUID id) {
        return super.factoryDeleteWithFilter(id, null);
    }

    @Override
    public ResponseEntity<BaseResponse<FactoryGetResponse<UUID, BedCategoriesDetail>>> getDetailById(UUID id) {
        return super.factoryGetDetailById(id);
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<BedCategoriesInfo>>> getInfoPageWithFilter(SimpleFilter filter, Integer number, Integer size) {
        return super.factoryGetInfoPageWithFilter(filter, number, size);
    }

    @Override
    public ResponseEntity<BaseResponse<BedCategoriesDetail>> updateModel(FactoryUpdateRequest<UUID, BedCategoriesDetail> request) {
        return super.factoryUpdateModel(request);
    }
}
