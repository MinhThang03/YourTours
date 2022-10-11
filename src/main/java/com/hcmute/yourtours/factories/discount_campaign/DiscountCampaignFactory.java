package com.hcmute.yourtours.factories.discount_campaign;

import com.hcmute.yourtours.commands.DiscountCampaignCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.discount_campaign.DiscountCampaignDetail;
import com.hcmute.yourtours.models.discount_campaign.DiscountCampaignInfo;
import com.hcmute.yourtours.repositories.DiscountCampaignRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DiscountCampaignFactory
        extends BasePersistDataFactory<UUID, DiscountCampaignInfo, DiscountCampaignDetail, Long, DiscountCampaignCommand>
        implements IDiscountCampaignFactory {

    private final DiscountCampaignRepository discountCampaignRepository;

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
    public DiscountCampaignCommand createConvertToEntity(DiscountCampaignDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return DiscountCampaignCommand.builder()
                .codeName(detail.getCodeName())
                .name(detail.getName())
                .description(detail.getDescription())
                .percent(detail.getPercent())
                .dateStart(detail.getDateStart())
                .dateEnd(detail.getDateEnd())
                .homeId(detail.getHomeId())
                .build();
    }

    @Override
    public void updateConvertToEntity(DiscountCampaignCommand entity, DiscountCampaignDetail detail) throws InvalidException {
        entity.setCodeName(detail.getCodeName());
        entity.setName(detail.getName());
        entity.setDescription(detail.getDescription());
        entity.setPercent(detail.getPercent());
        entity.setDateStart(detail.getDateStart());
        entity.setDateEnd(detail.getDateEnd());
        entity.setHomeId(detail.getHomeId());

    }

    @Override
    public DiscountCampaignDetail convertToDetail(DiscountCampaignCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return DiscountCampaignDetail.builder()
                .id(entity.getDiscountCampaignId())
                .codeName(entity.getCodeName())
                .name(entity.getName())
                .description(entity.getDescription())
                .percent(entity.getPercent())
                .dateStart(entity.getDateStart())
                .dateEnd(entity.getDateEnd())
                .homeId(entity.getHomeId())
                .build();
    }

    @Override
    public DiscountCampaignInfo convertToInfo(DiscountCampaignCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return DiscountCampaignInfo.builder()
                .id(entity.getDiscountCampaignId())
                .codeName(entity.getCodeName())
                .name(entity.getName())
                .description(entity.getDescription())
                .percent(entity.getPercent())
                .dateStart(entity.getDateStart())
                .dateEnd(entity.getDateEnd())
                .homeId(entity.getHomeId())
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        DiscountCampaignCommand command = discountCampaignRepository.findByDiscountCampaignId(id).orElse(null);
        if (command == null) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_DISCOUNT_CAMPAIGN);
        }
        return command.getId();
    }
}
