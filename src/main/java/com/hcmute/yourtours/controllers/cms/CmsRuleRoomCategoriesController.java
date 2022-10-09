package com.hcmute.yourtours.controllers.cms;

import com.hcmute.yourtours.controllers.cms.interfaces.ICmsRuleRoomCategoriesController;
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
import com.hcmute.yourtours.models.rule_home_categories.RuleHomeCategoryDetail;
import com.hcmute.yourtours.models.rule_home_categories.RuleHomeCategoryInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cms/rule-home-categories")
@Tag(name = "CMS API: RULE HOME CATEGORIES", description = "API admin config các loại nội quy của căn nhà")
@Transactional
public class CmsRuleRoomCategoriesController
        extends BaseController<UUID, RuleHomeCategoryInfo, RuleHomeCategoryDetail>
        implements ICmsRuleRoomCategoriesController {

    protected CmsRuleRoomCategoriesController(IDataFactory<UUID, RuleHomeCategoryInfo, RuleHomeCategoryDetail> iDataFactory, IResponseFactory iResponseFactory) {
        super(iDataFactory, iResponseFactory);
    }

    @Override
    public ResponseEntity<BaseResponse<RuleHomeCategoryDetail>> createModel(FactoryCreateRequest<UUID, RuleHomeCategoryDetail> request) {
        return super.factoryCreateModel(request);
    }

    @Override
    public ResponseEntity<BaseResponse<FactoryDeleteResponse>> deleteModelById(UUID id) {
        return super.factoryDeleteWithFilter(id, null);
    }

    @Override
    public ResponseEntity<BaseResponse<FactoryGetResponse<UUID, RuleHomeCategoryDetail>>> getDetailById(UUID id) {
        return super.factoryGetDetailById(id);
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<RuleHomeCategoryInfo>>> getInfoPageWithFilter(SimpleFilter filter, Integer number, Integer size) {
        return super.factoryGetInfoPageWithFilter(filter, number, size);
    }

    @Override
    public ResponseEntity<BaseResponse<RuleHomeCategoryDetail>> updateModel(FactoryUpdateRequest<UUID, RuleHomeCategoryDetail> request) {
        return super.factoryUpdateModel(request);
    }
}
