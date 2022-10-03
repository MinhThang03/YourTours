package com.hcmute.yourtours.factories.surcharge_home_categories;

import com.hcmute.yourtours.commands.SurchargeHomeCategoriesCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.surcharge_home_categories.SurchargeHomeCategoriesDetail;
import com.hcmute.yourtours.models.surcharge_home_categories.SurchargeHomeCategoriesInfo;
import com.hcmute.yourtours.repositories.SurchargeHomeCategoriesRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class SurchargeHomeCategoriesFactory
        extends BasePersistDataFactory<UUID, SurchargeHomeCategoriesInfo, SurchargeHomeCategoriesDetail, Long, SurchargeHomeCategoriesCommand>
        implements ISurchargeHomeCategoriesFactory {

    private final SurchargeHomeCategoriesRepository surchargeRepository;

    protected SurchargeHomeCategoriesFactory(SurchargeHomeCategoriesRepository repository) {
        super(repository);
        this.surchargeRepository = repository;
    }

    @Override
    @NonNull
    protected Class<SurchargeHomeCategoriesDetail> getDetailClass() {
        return SurchargeHomeCategoriesDetail.class;
    }

    @Override
    public SurchargeHomeCategoriesCommand createConvertToEntity(SurchargeHomeCategoriesDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return SurchargeHomeCategoriesCommand.builder()
                .name(detail.getName())
                .description(detail.getDescription())
                .status(detail.getStatus())
                .build();
    }

    @Override
    public void updateConvertToEntity(SurchargeHomeCategoriesCommand entity, SurchargeHomeCategoriesDetail detail) throws InvalidException {
        entity.setName(detail.getName());
        entity.setDescription(detail.getDescription());
        entity.setStatus(detail.getStatus());
    }

    @Override
    public SurchargeHomeCategoriesDetail convertToDetail(SurchargeHomeCategoriesCommand entity) throws InvalidException {
        return (SurchargeHomeCategoriesDetail) convertToInfo(entity);
    }

    @Override
    public SurchargeHomeCategoriesInfo convertToInfo(SurchargeHomeCategoriesCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return SurchargeHomeCategoriesInfo.builder()
                .id(entity.getSurchargeCategoryId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        Optional<SurchargeHomeCategoriesCommand> optional = surchargeRepository.findBySurchargeCategoryId(id);
        if (optional.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_SURCHARGE_CATEGORIES);
        }
        return optional.get().getId();
    }
}
