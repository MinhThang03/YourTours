package com.hcmute.yourtours.controllers.cms;

import com.hcmute.yourtours.controllers.cms.interfaces.ICmsSecurityCategoriesController;
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
import com.hcmute.yourtours.models.security_categories.SecurityCategoryDetail;
import com.hcmute.yourtours.models.security_categories.SecurityCategoryInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cms/security-categories")
@Tag(name = "CMS API: SECURITY CATEGORIES", description = "API admin config các loại bảo mật")
@Transactional
public class CmsSecurityCategoriesController
        extends BaseController<UUID, SecurityCategoryInfo, SecurityCategoryDetail>
        implements ICmsSecurityCategoriesController {

    protected CmsSecurityCategoriesController(IDataFactory<UUID, SecurityCategoryInfo, SecurityCategoryDetail> iDataFactory, IResponseFactory iResponseFactory) {
        super(iDataFactory, iResponseFactory);
    }

    @Override
    public ResponseEntity<BaseResponse<SecurityCategoryDetail>> createModel(FactoryCreateRequest<UUID, SecurityCategoryDetail> request) {
        return super.factoryCreateModel(request);
    }

    @Override
    public ResponseEntity<BaseResponse<FactoryDeleteResponse>> deleteModelById(UUID id) {
        return super.factoryDeleteWithFilter(id, null);
    }

    @Override
    public ResponseEntity<BaseResponse<FactoryGetResponse<UUID, SecurityCategoryDetail>>> getDetailById(UUID id) {
        return super.factoryGetDetailById(id);
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<SecurityCategoryInfo>>> getInfoPageWithFilter(SimpleFilter filter, Integer number, Integer size) {
        return super.factoryGetInfoPageWithFilter(filter, number, size);
    }

    @Override
    public ResponseEntity<BaseResponse<SecurityCategoryDetail>> updateModel(FactoryUpdateRequest<UUID, SecurityCategoryDetail> request) {
        return super.factoryUpdateModel(request);
    }
}
