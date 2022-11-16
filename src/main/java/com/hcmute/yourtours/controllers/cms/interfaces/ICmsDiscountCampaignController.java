package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.*;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.discount_campaign.DiscountCampaignDetail;
import com.hcmute.yourtours.models.discount_campaign.DiscountCampaignInfo;

import java.util.UUID;

public interface ICmsDiscountCampaignController extends
        ICreateModelController<UUID, DiscountCampaignDetail>,
        IUpdateModelController<UUID, DiscountCampaignDetail>,
        IDeleteModelByIdController<UUID>,
        IGetInfoPageController<UUID, DiscountCampaignInfo, BaseFilter>,
        IGetDetailByIdController<UUID, DiscountCampaignDetail> {
}
