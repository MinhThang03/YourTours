package com.hcmute.yourtours.factories.discount_campaign;

import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.discount_campaign.DiscountCampaignDetail;
import com.hcmute.yourtours.models.discount_campaign.DiscountCampaignInfo;

import java.util.UUID;

public interface IDiscountCampaignFactory extends IDataFactory<UUID, DiscountCampaignInfo, DiscountCampaignDetail> {
}
