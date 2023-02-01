package com.hcmute.yourtours.factories.owner_of_home;

import com.hcmute.yourtours.entities.OwnerOfHome;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.owner_of_home.OwnerOfHomeDetail;
import com.hcmute.yourtours.models.owner_of_home.OwnerOfHomeInfo;
import com.hcmute.yourtours.models.owner_of_home.models.StatisticInfoOwnerModel;
import com.hcmute.yourtours.models.owner_of_home.projections.StatisticInfoOwnerProjection;
import com.hcmute.yourtours.repositories.OwnerOfHomesRepository;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class OwnerOfHomeFactory
        extends BasePersistDataFactory<UUID, OwnerOfHomeInfo, OwnerOfHomeDetail, Long, OwnerOfHome>
        implements IOwnerOfHomeFactory {

    private final OwnerOfHomesRepository ownerOfHomesRepository;

    protected OwnerOfHomeFactory(OwnerOfHomesRepository repository) {
        super(repository);
        this.ownerOfHomesRepository = repository;
    }

    @Override
    @NonNull
    protected Class<OwnerOfHomeDetail> getDetailClass() {
        return OwnerOfHomeDetail.class;
    }

    @Override
    public OwnerOfHome createConvertToEntity(OwnerOfHomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return OwnerOfHome.builder()
                .isMainOwner(detail.getIsMainOwner())
                .userId(detail.getUserId())
                .homeId(detail.getHomeId())
                .build();
    }

    @Override
    public void updateConvertToEntity(OwnerOfHome entity, OwnerOfHomeDetail detail) throws InvalidException {
        entity.setHomeId(detail.getHomeId());
        entity.setIsMainOwner(detail.getIsMainOwner());
        entity.setHomeId(detail.getHomeId());
    }

    @Override
    public OwnerOfHomeDetail convertToDetail(OwnerOfHome entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return OwnerOfHomeDetail.builder()
                .id(entity.getOwnerOfHomeId())
                .isMainOwner(entity.getIsMainOwner())
                .homeId(entity.getHomeId())
                .userId(entity.getUserId())
                .build();
    }

    @Override
    public OwnerOfHomeInfo convertToInfo(OwnerOfHome entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return OwnerOfHomeInfo.builder()
                .id(entity.getOwnerOfHomeId())
                .isMainOwner(entity.getIsMainOwner())
                .homeId(entity.getHomeId())
                .userId(entity.getUserId())
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        OwnerOfHome command = ownerOfHomesRepository.findByOwnerOfHomeId(id).orElse(null);
        if (command == null) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_OWNER_OF_HOME);
        }
        return command.getId();
    }

    @Override
    public boolean existByOwnerIdAndHomeId(UUID ownerId, UUID homeId) {
        return ownerOfHomesRepository.existsByUserIdAndHomeId(ownerId, homeId);
    }

    @Override
    public String getMainOwnerOfHome(UUID homeId) {
        return ownerOfHomesRepository.getMainOwnerNameOfHome(homeId);
    }

    @Override
    public BasePagingResponse<StatisticInfoOwnerModel> getStatisticInfoOwner(BaseFilter filter, Integer number, Integer size) {
        Page<StatisticInfoOwnerProjection> projections = ownerOfHomesRepository.getStatisticInfoOwner(PageRequest.of(number, size));
        List<StatisticInfoOwnerModel> result = projections.stream().map
                (
                        item -> StatisticInfoOwnerModel.builder()
                                .numberOfHomes(item.getNumberOfHomes())
                                .numberOfBooking(item.getNumberOfBooking())
                                .totalCost(item.getTotalCost())
                                .fullName(item.getFullName())
                                .userId(item.getUserId())
                                .build()
                ).collect(Collectors.toList());

        return new BasePagingResponse<>(
                result,
                number,
                size,
                projections.getTotalElements()
        );
    }
}
