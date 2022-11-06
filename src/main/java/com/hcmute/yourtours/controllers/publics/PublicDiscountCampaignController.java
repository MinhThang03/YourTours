package com.hcmute.yourtours.controllers.publics;

import com.hcmute.yourtours.controllers.publics.interfaces.IPublicDiscountCampaignController;
import com.hcmute.yourtours.factories.discount_campaign.IDiscountCampaignFactory;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.libs.model.filter.SimpleFilter;
import com.hcmute.yourtours.models.discount_campaign.DiscountCampaignDetail;
import com.hcmute.yourtours.models.discount_campaign.DiscountCampaignInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/public/discounts-campaign")
@Tag(name = "PUBLIC API: discount campaign", description = "API lấy danh sách chiến dịch giảm giá")
@Transactional
public class PublicDiscountCampaignController
        extends BaseController<UUID, DiscountCampaignInfo, DiscountCampaignDetail>
        implements IPublicDiscountCampaignController {


    protected PublicDiscountCampaignController
            (
                    @Qualifier("appDiscountCampaignFactory") IDiscountCampaignFactory iDataFactory,
                    IResponseFactory iResponseFactory
            ) {
        super(iDataFactory, iResponseFactory);
    }


    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<DiscountCampaignInfo>>> getInfoPageWithFilter(SimpleFilter filter, Integer number, Integer size) {
        return factoryGetInfoPageWithFilter(filter, number, size);
    }
}
