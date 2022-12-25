package com.hcmute.yourtours.controllers.cms;

import com.hcmute.yourtours.controllers.cms.interfaces.ICmsDiscountCampaignController;
import com.hcmute.yourtours.factories.discount_campaign.IDiscountCampaignFactory;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.model.factory.request.FactoryCreateRequest;
import com.hcmute.yourtours.libs.model.factory.request.FactoryUpdateRequest;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.libs.model.factory.response.FactoryDeleteResponse;
import com.hcmute.yourtours.libs.model.factory.response.FactoryGetResponse;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.discount_campaign.DiscountCampaignDetail;
import com.hcmute.yourtours.models.discount_campaign.DiscountCampaignInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cms/discount-campaign")
@Tag(name = "CMS API: DISCOUNT CAMPAIGN", description = "API admin quản lý discount campaign")
@Transactional
public class CmsDiscountCampaignController
        extends BaseController<UUID, DiscountCampaignInfo, DiscountCampaignDetail>
        implements ICmsDiscountCampaignController {


    protected CmsDiscountCampaignController(
            @Qualifier("discountCampaignFactory") IDiscountCampaignFactory iDataFactory,
            IResponseFactory iResponseFactory) {
        super(iDataFactory, iResponseFactory);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<DiscountCampaignDetail>> createModel(FactoryCreateRequest<UUID, DiscountCampaignDetail> request) {
        return super.factoryCreateModel(request);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<FactoryDeleteResponse>> deleteModelById(UUID id) {
        return super.factoryDeleteWithFilter(id, null);
    }

    @Override
    public ResponseEntity<BaseResponse<FactoryGetResponse<UUID, DiscountCampaignDetail>>> getDetailById(UUID id) {
        return super.factoryGetDetailById(id);
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<DiscountCampaignInfo>>> getInfoPageWithFilter(BaseFilter filter, Integer number, Integer size) {
        return super.factoryGetInfoPageWithFilter(filter, number, size);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<DiscountCampaignDetail>> updateModel(FactoryUpdateRequest<UUID, DiscountCampaignDetail> request) {
        return super.factoryUpdateModel(request);
    }
}
