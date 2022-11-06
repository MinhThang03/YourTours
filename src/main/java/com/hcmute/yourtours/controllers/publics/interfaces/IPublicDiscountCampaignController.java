package com.hcmute.yourtours.controllers.publics.interfaces;

import com.hcmute.yourtours.libs.controller.IGetInfoPageController;
import com.hcmute.yourtours.libs.model.filter.SimpleFilter;
import com.hcmute.yourtours.models.discount_campaign.DiscountCampaignInfo;

import java.util.UUID;

public interface IPublicDiscountCampaignController extends
        IGetInfoPageController<UUID, DiscountCampaignInfo, SimpleFilter> {
}
