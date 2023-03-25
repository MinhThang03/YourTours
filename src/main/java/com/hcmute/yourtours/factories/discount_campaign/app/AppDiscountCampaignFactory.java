package com.hcmute.yourtours.factories.discount_campaign.app;

import com.hcmute.yourtours.entities.DiscountCampaign;
import com.hcmute.yourtours.factories.discount_campaign.DiscountCampaignFactory;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.repositories.DiscountCampaignRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AppDiscountCampaignFactory extends DiscountCampaignFactory {

    protected AppDiscountCampaignFactory(DiscountCampaignRepository repository) {
        super(repository);
    }

    @Override
    protected <F extends BaseFilter> Page<DiscountCampaign> pageQuery(F filter, Integer number, Integer size) {
        return discountCampaignRepository.findAllByDateEndAfter(LocalDate.now(), PageRequest.of(number, size));
    }
}
