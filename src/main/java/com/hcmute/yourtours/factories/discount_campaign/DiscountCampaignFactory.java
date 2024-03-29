package com.hcmute.yourtours.factories.discount_campaign;

import com.hcmute.yourtours.entities.DiscountCampaign;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.discount_campaign.DiscountCampaignDetail;
import com.hcmute.yourtours.models.discount_campaign.DiscountCampaignInfo;
import com.hcmute.yourtours.repositories.DiscountCampaignRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class DiscountCampaignFactory
        extends BasePersistDataFactory<UUID, DiscountCampaignInfo, DiscountCampaignDetail, UUID, DiscountCampaign>
        implements IDiscountCampaignFactory {

    protected final DiscountCampaignRepository discountCampaignRepository;

    protected DiscountCampaignFactory(DiscountCampaignRepository repository) {
        super(repository);
        this.discountCampaignRepository = repository;
    }

    @Override
    @NonNull
    protected Class<DiscountCampaignDetail> getDetailClass() {
        return DiscountCampaignDetail.class;
    }

    @Override
    public DiscountCampaign createConvertToEntity(DiscountCampaignDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return DiscountCampaign.builder()
                .codeName(detail.getCodeName())
                .name(detail.getName())
                .description(detail.getDescription())
                .percent(detail.getPercent())
                .dateStart(detail.getDateStart())
                .dateEnd(detail.getDateEnd())
                .homeId(detail.getHomeId())
                .banner(detail.getBanner())
                .build();
    }

    @Override
    public void updateConvertToEntity(DiscountCampaign entity, DiscountCampaignDetail detail) throws InvalidException {
        entity.setCodeName(detail.getCodeName());
        entity.setName(detail.getName());
        entity.setDescription(detail.getDescription());
        entity.setPercent(detail.getPercent());
        entity.setDateStart(detail.getDateStart());
        entity.setDateEnd(detail.getDateEnd());
        entity.setHomeId(detail.getHomeId());
        entity.setBanner(detail.getBanner());

    }

    @Override
    public DiscountCampaignDetail convertToDetail(DiscountCampaign entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return DiscountCampaignDetail.builder()
                .id(entity.getId())
                .codeName(entity.getCodeName())
                .name(entity.getName())
                .description(entity.getDescription())
                .percent(entity.getPercent())
                .dateStart(entity.getDateStart())
                .dateEnd(entity.getDateEnd())
                .homeId(entity.getHomeId())
                .banner(entity.getBanner())
                .build();
    }

    @Override
    public DiscountCampaignInfo convertToInfo(DiscountCampaign entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return DiscountCampaignInfo.builder()
                .id(entity.getId())
                .codeName(entity.getCodeName())
                .name(entity.getName())
                .description(entity.getDescription())
                .percent(entity.getPercent())
                .dateStart(entity.getDateStart())
                .dateEnd(entity.getDateEnd())
                .homeId(entity.getHomeId())
                .banner(entity.getBanner())
                .build();
    }

}
