package com.hcmute.yourtours.controllers.cms;

import com.hcmute.yourtours.controllers.cms.interfaces.ICmsSurchargeHomeController;
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
import com.hcmute.yourtours.models.surcharge_home_categories.SurchargeHomeCategoryDetail;
import com.hcmute.yourtours.models.surcharge_home_categories.SurchargeHomeCategoryInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cms/surcharge-home-categories")
@Tag(name = "CMS API: SURCHARGE HOME CATEGORIES", description = "API admin config các loại phụ phí của căn nhà")
@Transactional
public class CmsSurchargeHomeController
        extends BaseController<UUID, SurchargeHomeCategoryInfo, SurchargeHomeCategoryDetail>
        implements ICmsSurchargeHomeController {

    protected CmsSurchargeHomeController(IDataFactory<UUID, SurchargeHomeCategoryInfo, SurchargeHomeCategoryDetail> iDataFactory, IResponseFactory iResponseFactory) {
        super(iDataFactory, iResponseFactory);
    }

    @Override
    public ResponseEntity<BaseResponse<SurchargeHomeCategoryDetail>> createModel(FactoryCreateRequest<UUID, SurchargeHomeCategoryDetail> request) {
        return super.factoryCreateModel(request);
    }

    @Override
    public ResponseEntity<BaseResponse<FactoryDeleteResponse>> deleteModelById(UUID id) {
        return super.factoryDeleteWithFilter(id, null);
    }

    @Override
    public ResponseEntity<BaseResponse<FactoryGetResponse<UUID, SurchargeHomeCategoryDetail>>> getDetailById(UUID id) {
        return super.factoryGetDetailById(id);
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<SurchargeHomeCategoryInfo>>> getInfoPageWithFilter(SimpleFilter filter, Integer number, Integer size) {
        return super.factoryGetInfoPageWithFilter(filter, number, size);
    }

    @Override
    public ResponseEntity<BaseResponse<SurchargeHomeCategoryDetail>> updateModel(FactoryUpdateRequest<UUID, SurchargeHomeCategoryDetail> request) {
        return super.factoryUpdateModel(request);
    }
}
