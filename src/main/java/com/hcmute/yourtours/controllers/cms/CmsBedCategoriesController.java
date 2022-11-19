package com.hcmute.yourtours.controllers.cms;


import com.hcmute.yourtours.controllers.cms.interfaces.ICmsBedCategoriesController;
import com.hcmute.yourtours.factories.bed_categories.IBedCategoriesFactory;
import com.hcmute.yourtours.factories.bed_categories.cms.ICmsBedCategoryFactory;
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
import com.hcmute.yourtours.libs.model.filter.SimpleFilter;
import com.hcmute.yourtours.models.bed_categories.BedCategoryDetail;
import com.hcmute.yourtours.models.bed_categories.BedCategoryInfo;
import com.hcmute.yourtours.models.bed_categories.filter.BedCategoryFilter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
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
        extends BaseController<UUID, BedCategoryInfo, BedCategoryDetail>
        implements ICmsBedCategoriesController {

    private final ICmsBedCategoryFactory iCmsBedCategoryFactory;

    protected CmsBedCategoriesController
            (
                    @Qualifier("bedCategoriesFactory") IBedCategoriesFactory iDataFactory,
                    IResponseFactory iResponseFactory,
                    ICmsBedCategoryFactory iCmsBedCategoryFactory
            ) {
        super(iDataFactory, iResponseFactory);
        this.iCmsBedCategoryFactory = iCmsBedCategoryFactory;
    }

    @Override
    public ResponseEntity<BaseResponse<BedCategoryDetail>> createModel(FactoryCreateRequest<UUID, BedCategoryDetail> request) {
        return super.factoryCreateModel(request);
    }

    @Override
    public ResponseEntity<BaseResponse<FactoryDeleteResponse>> deleteModelById(UUID id) {
        return super.factoryDeleteWithFilter(id, null);
    }

    @Override
    public ResponseEntity<BaseResponse<FactoryGetResponse<UUID, BedCategoryDetail>>> getDetailById(UUID id) {
        return super.factoryGetDetailById(id);
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<BedCategoryInfo>>> getInfoPageWithFilter(SimpleFilter filter, Integer number, Integer size) {
        return super.factoryGetInfoPageWithFilter(filter, number, size);
    }

    @Override
    public ResponseEntity<BaseResponse<BedCategoryDetail>> updateModel(FactoryUpdateRequest<UUID, BedCategoryDetail> request) {
        return super.factoryUpdateModel(request);
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<BedCategoryInfo>>> getInfoPageWithRoomId(BedCategoryFilter filter, Integer number, Integer size) {
        try {
            BasePagingResponse<BedCategoryInfo> response = iCmsBedCategoryFactory.aroundGetPageWithRoomHomeId(filter, number, size);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }
}
