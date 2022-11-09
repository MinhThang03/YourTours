package com.hcmute.yourtours.factories.surcharges_of_home;

import com.hcmute.yourtours.commands.SurchargesOfHomeCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.surcharges_of_home.SurchargeOfHomeDetail;
import com.hcmute.yourtours.models.surcharges_of_home.SurchargeOfHomeInfo;
import com.hcmute.yourtours.models.surcharges_of_home.models.SurchargeHomeViewModel;
import com.hcmute.yourtours.models.surcharges_of_home.projections.SurchargeHomeViewProjection;
import com.hcmute.yourtours.repositories.SurchargesOfHomeRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class SurchargesOfHomeFactory
        extends BasePersistDataFactory<UUID, SurchargeOfHomeInfo, SurchargeOfHomeDetail, Long, SurchargesOfHomeCommand>
        implements ISurchargeOfHomeFactory {

    private final SurchargesOfHomeRepository surchargesOfHomeRepository;

    protected SurchargesOfHomeFactory(SurchargesOfHomeRepository repository) {
        super(repository);
        this.surchargesOfHomeRepository = repository;
    }

    @Override
    @NonNull
    protected Class<SurchargeOfHomeDetail> getDetailClass() {
        return SurchargeOfHomeDetail.class;
    }

    @Override
    public SurchargesOfHomeCommand createConvertToEntity(SurchargeOfHomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return SurchargesOfHomeCommand.builder()
                .cost(detail.getCost())
                .categoryId(detail.getCategoryId())
                .homeId(detail.getHomeId())
                .build();
    }

    @Override
    public void updateConvertToEntity(SurchargesOfHomeCommand entity, SurchargeOfHomeDetail detail) throws InvalidException {
        entity.setHomeId(detail.getHomeId());
        entity.setCost(detail.getCost());
        entity.setCategoryId(detail.getCategoryId());
    }

    @Override
    public SurchargeOfHomeDetail convertToDetail(SurchargesOfHomeCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return SurchargeOfHomeDetail.builder()
                .id(entity.getSurchargeOfHomeId())
                .cost(entity.getCost())
                .categoryId(entity.getCategoryId())
                .homeId(entity.getHomeId())
                .build();
    }

    @Override
    public SurchargeOfHomeInfo convertToInfo(SurchargesOfHomeCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return SurchargeOfHomeInfo.builder()
                .id(entity.getSurchargeOfHomeId())
                .cost(entity.getCost())
                .categoryId(entity.getCategoryId())
                .homeId(entity.getHomeId())
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        SurchargesOfHomeCommand command = surchargesOfHomeRepository.findBySurchargeOfHomeId(id).orElse(null);
        if (command == null) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_SURCHARGES_OF_HOME);
        }
        return command.getId();
    }

    @Override
    public List<SurchargeHomeViewModel> getListCategoryWithHomeId(UUID homeId) {
        List<SurchargeHomeViewProjection> projections = surchargesOfHomeRepository.getListCategoryWithHomeId(homeId);
        return projections.stream().map
                        (
                                item -> SurchargeHomeViewModel.builder()
                                        .surchargeCategoryId(item.getSurchargeCategoryId())
                                        .surchargeCategoryName(item.getSurchargeCategoryName())
                                        .description(item.getDescription())
                                        .cost(item.getCost())
                                        .build()
                        )
                .collect(Collectors.toList());
    }

    @Override
    protected void preCreate(SurchargeOfHomeDetail detail) throws InvalidException {
        surchargesOfHomeRepository.deleteAllByHomeIdAndCategoryId(detail.getHomeId(), detail.getCategoryId());
    }
}
