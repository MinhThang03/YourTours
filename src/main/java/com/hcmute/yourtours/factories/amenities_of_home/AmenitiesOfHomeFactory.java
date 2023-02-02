package com.hcmute.yourtours.factories.amenities_of_home;

import com.hcmute.yourtours.entities.AmenitiesOfHome;
import com.hcmute.yourtours.factories.common.IAuthorizationOwnerHomeFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.amenities_of_home.AmenityOfHomeDetail;
import com.hcmute.yourtours.models.amenities_of_home.AmenityOfHomeInfo;
import com.hcmute.yourtours.models.amenities_of_home.projection.AmenityOfHomeModel;
import com.hcmute.yourtours.models.amenities_of_home.projection.AmenityOfHomeProjection;
import com.hcmute.yourtours.repositories.AmenitiesOfHomeRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class AmenitiesOfHomeFactory
        extends BasePersistDataFactory<UUID, AmenityOfHomeInfo, AmenityOfHomeDetail, UUID, AmenitiesOfHome>
        implements IAmenitiesOfHomeFactory {

    private final AmenitiesOfHomeRepository amenitiesOfHomeRepository;
    private final IAuthorizationOwnerHomeFactory iAuthorizationOwnerHomeFactory;

    protected AmenitiesOfHomeFactory(AmenitiesOfHomeRepository repository,
                                     IAuthorizationOwnerHomeFactory iAuthorizationOwnerHomeFactory) {
        super(repository);
        this.amenitiesOfHomeRepository = repository;
        this.iAuthorizationOwnerHomeFactory = iAuthorizationOwnerHomeFactory;
    }

    @Override
    @NonNull
    protected Class<AmenityOfHomeDetail> getDetailClass() {
        return AmenityOfHomeDetail.class;
    }

    @Override
    public AmenitiesOfHome createConvertToEntity(AmenityOfHomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return AmenitiesOfHome.builder()
                .isHave(detail.getIsHave())
                .amenityId(detail.getAmenityId())
                .homeId(detail.getHomeId())
                .build();
    }

    @Override
    public void updateConvertToEntity(AmenitiesOfHome entity, AmenityOfHomeDetail detail) throws InvalidException {
        entity.setIsHave(detail.getIsHave());
        entity.setAmenityId(detail.getAmenityId());
        entity.setHomeId(detail.getHomeId());
    }

    @Override
    public AmenityOfHomeDetail convertToDetail(AmenitiesOfHome entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return AmenityOfHomeDetail.builder()
                .id(entity.getId())
                .isHave(entity.getIsHave())
                .amenityId(entity.getAmenityId())
                .homeId(entity.getHomeId())
                .build();
    }

    @Override
    public AmenityOfHomeInfo convertToInfo(AmenitiesOfHome entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return AmenityOfHomeInfo.builder()
                .id(entity.getId())
                .isHave(entity.getIsHave())
                .amenityId(entity.getAmenityId())
                .homeId(entity.getHomeId())
                .build();
    }


    @Override
    public List<AmenityOfHomeModel> getAllByCategoryIdAndHomeId(UUID categoryId, UUID homeId) {
        List<AmenityOfHomeProjection> projections = amenitiesOfHomeRepository
                .findAllByAmenityCategoryIdAndHomeId(categoryId, homeId);

        return projections.stream().map(item -> AmenityOfHomeModel.builder()
                .name(item.getName())
                .description(item.getDescription())
                .status(item.getStatus())
                .isHave(item.getIsHave())
                .amenityId(item.getAmenityId())
                .homeId(item.getHomeId())
                .build()).collect(Collectors.toList());
    }

    @Override
    public void createListWithHomeId(UUID homeId, List<AmenityOfHomeDetail> listCreate) throws InvalidException {
        if (listCreate != null) {
            for (AmenityOfHomeDetail item : listCreate) {
                item.setHomeId(homeId);
                item.setIsHave(true);
                createModel(item);
            }
        }

    }

    @Override
    protected void preCreate(AmenityOfHomeDetail detail) throws InvalidException {
        iAuthorizationOwnerHomeFactory.checkOwnerOfHome(detail.getHomeId());
    }

    @Override
    protected AmenityOfHomeDetail aroundCreate(AmenityOfHomeDetail detail) throws InvalidException {
        Optional<AmenitiesOfHome> optional = amenitiesOfHomeRepository
                .findByHomeIdAndAmenityId(detail.getHomeId(), detail.getAmenityId());

        if (optional.isPresent()) {
            Boolean isHave = optional.get().getIsHave();
            if (isHave != null && isHave.equals(detail.getIsHave())) {
                detail.setIsHave(null);
            }
            return updateModel(optional.get().getId(), detail);
        }

        return super.aroundCreate(detail);
    }
}
