package com.hcmute.yourtours.controllers.cms;

import com.hcmute.yourtours.controllers.cms.interfaces.ICmsDiscountHomeController;
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
import com.hcmute.yourtours.models.discount_home_categories.DiscountHomeCategoryDetail;
import com.hcmute.yourtours.models.discount_home_categories.DiscountHomeCategoryInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cms/discount-home-categories")
@Tag(name = "CMS API: DISCOUNT HOME CATEGORIES", description = "API admin config các loại giảm giá của căn nhà")
@Transactional
public class CmsDiscountHomeController
        extends BaseController<UUID, DiscountHomeCategoryInfo, DiscountHomeCategoryDetail>
        implements ICmsDiscountHomeController {

    protected CmsDiscountHomeController(IDataFactory<UUID, DiscountHomeCategoryInfo, DiscountHomeCategoryDetail> iDataFactory, IResponseFactory iResponseFactory) {
        super(iDataFactory, iResponseFactory);
    }

    @Override
    public ResponseEntity<BaseResponse<DiscountHomeCategoryDetail>> createModel(FactoryCreateRequest<UUID, DiscountHomeCategoryDetail> request) {
        return super.factoryCreateModel(request);
    }

    @Override
    public ResponseEntity<BaseResponse<FactoryDeleteResponse>> deleteModelById(UUID id) {
        return super.factoryDeleteWithFilter(id, null);
    }

    @Override
    public ResponseEntity<BaseResponse<FactoryGetResponse<UUID, DiscountHomeCategoryDetail>>> getDetailById(UUID id) {
        return super.factoryGetDetailById(id);
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<DiscountHomeCategoryInfo>>> getInfoPageWithFilter(SimpleFilter filter, Integer number, Integer size) {
        return super.factoryGetInfoPageWithFilter(filter, number, size);
    }

    @Override
    public ResponseEntity<BaseResponse<DiscountHomeCategoryDetail>> updateModel(FactoryUpdateRequest<UUID, DiscountHomeCategoryDetail> request) {
        return super.factoryUpdateModel(request);
    }
}
