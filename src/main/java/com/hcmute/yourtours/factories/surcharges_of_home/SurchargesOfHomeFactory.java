package com.hcmute.yourtours.factories.surcharges_of_home;

import com.hcmute.yourtours.entities.SurchargesOfHome;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.surcharges_of_home.SurchargeOfHomeDetail;
import com.hcmute.yourtours.models.surcharges_of_home.SurchargeOfHomeInfo;
import com.hcmute.yourtours.models.surcharges_of_home.models.CreateListSurchargeHomeModel;
import com.hcmute.yourtours.models.surcharges_of_home.models.SurchargeHomeViewModel;
import com.hcmute.yourtours.models.surcharges_of_home.projections.SurchargeHomeViewProjection;
import com.hcmute.yourtours.repositories.SurchargesOfHomeRepository;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class SurchargesOfHomeFactory
        extends BasePersistDataFactory<UUID, SurchargeOfHomeInfo, SurchargeOfHomeDetail, Long, SurchargesOfHome>
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
    public SurchargesOfHome createConvertToEntity(SurchargeOfHomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return SurchargesOfHome.builder()
                .cost(detail.getCost())
                .categoryId(detail.getCategoryId())
                .homeId(detail.getHomeId())
                .build();
    }

    @Override
    public void updateConvertToEntity(SurchargesOfHome entity, SurchargeOfHomeDetail detail) throws InvalidException {
        entity.setHomeId(detail.getHomeId());
        entity.setCost(detail.getCost());
        entity.setCategoryId(detail.getCategoryId());
    }

    @Override
    public SurchargeOfHomeDetail convertToDetail(SurchargesOfHome entity) throws InvalidException {
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
    public SurchargeOfHomeInfo convertToInfo(SurchargesOfHome entity) throws InvalidException {
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
        SurchargesOfHome command = surchargesOfHomeRepository.findBySurchargeOfHomeId(id).orElse(null);
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
    public SuccessResponse createListSurchargeOfHome(CreateListSurchargeHomeModel request) throws InvalidException {
        if (request != null && request.getListSurchargeHomeDetail() != null) {
            for (SurchargeOfHomeDetail item : request.getListSurchargeHomeDetail()) {
                createModel(item);
            }
        }
        return SuccessResponse.builder()
                .success(true)
                .build();
    }

    @Override
    public BasePagingResponse<SurchargeHomeViewModel> getPageSurchargeOfHome(UUID homeId, Integer number, Integer size) {
        Page<SurchargeHomeViewProjection> page = surchargesOfHomeRepository.getPageByHomeId(homeId, PageRequest.of(number, size));

        List<SurchargeHomeViewModel> content = page.getContent().stream().map
                (
                        item -> SurchargeHomeViewModel.builder()
                                .surchargeCategoryId(item.getSurchargeCategoryId())
                                .surchargeCategoryName(item.getSurchargeCategoryName())
                                .description(item.getDescription())
                                .cost(item.getCost())
                                .build()
                ).collect(Collectors.toList());

        return new BasePagingResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements()
        );
    }

    @Override
    public List<SurchargeHomeViewModel> getListSurchargeOfHome(UUID homeId) {
        List<SurchargeHomeViewProjection> projections = surchargesOfHomeRepository.getListByHomeId(homeId);

        return projections.stream().map
                (
                        item -> SurchargeHomeViewModel.builder()
                                .surchargeCategoryId(item.getSurchargeCategoryId())
                                .surchargeCategoryName(item.getSurchargeCategoryName())
                                .description(item.getDescription())
                                .cost(item.getCost())
                                .build()
                ).collect(Collectors.toList());


    }

    @Override
    public SurchargeOfHomeDetail getByHomeIdAndCategoryId(UUID homeId, UUID categoryId) throws InvalidException {
        Optional<SurchargesOfHome> optional = surchargesOfHomeRepository.findByHomeIdAndCategoryId(homeId, categoryId);
        if (optional.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_SURCHARGES_OF_HOME);
        }
        return convertToDetail(optional.get());
    }


    @Override
    protected void preCreate(SurchargeOfHomeDetail detail) throws InvalidException {
        List<SurchargesOfHome> listDelete =
                surchargesOfHomeRepository.findAllByHomeIdAndCategoryId(detail.getHomeId(), detail.getCategoryId());
        repository.deleteAll(listDelete);
    }
}
