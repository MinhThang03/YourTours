package com.hcmute.yourtours.factories.amenities_of_home;

import com.hcmute.yourtours.commands.AmenitiesOfHomeCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.amenities_of_home.AmenityOfHomeDetail;
import com.hcmute.yourtours.models.amenities_of_home.AmenityOfHomeInfo;
import com.hcmute.yourtours.repositories.AmenitiesOfHomeRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class AmenitiesOfHomeFactory
        extends BasePersistDataFactory<UUID, AmenityOfHomeInfo, AmenityOfHomeDetail, Long, AmenitiesOfHomeCommand>
        implements IAmenitiesOfHomeFactory {

    private final AmenitiesOfHomeRepository amenitiesOfHomeRepository;

    protected AmenitiesOfHomeFactory(AmenitiesOfHomeRepository repository) {
        super(repository);
        this.amenitiesOfHomeRepository = repository;
    }

    @Override
    @NonNull
    protected Class<AmenityOfHomeDetail> getDetailClass() {
        return AmenityOfHomeDetail.class;
    }

    @Override
    public AmenitiesOfHomeCommand createConvertToEntity(AmenityOfHomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return AmenitiesOfHomeCommand.builder()
                .isHave(detail.getIsHave())
                .amenityId(detail.getAmenityId())
                .homeId(detail.getHomeId())
                .build();
    }

    @Override
    public void updateConvertToEntity(AmenitiesOfHomeCommand entity, AmenityOfHomeDetail detail) throws InvalidException {
        entity.setIsHave(detail.getIsHave());
        entity.setAmenityId(detail.getAmenityId());
        entity.setHomeId(detail.getHomeId());
    }

    @Override
    public AmenityOfHomeDetail convertToDetail(AmenitiesOfHomeCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return AmenityOfHomeDetail.builder()
                .id(entity.getAmenityOfHomeId())
                .isHave(entity.getIsHave())
                .amenityId(entity.getAmenityId())
                .homeId(entity.getHomeId())
                .build();
    }

    @Override
    public AmenityOfHomeInfo convertToInfo(AmenitiesOfHomeCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return AmenityOfHomeInfo.builder()
                .id(entity.getAmenityOfHomeId())
                .isHave(entity.getIsHave())
                .amenityId(entity.getAmenityId())
                .homeId(entity.getHomeId())
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        AmenitiesOfHomeCommand command = amenitiesOfHomeRepository.findByAmenityOfHomeId(id).orElse(null);
        if (command == null) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_AMENITIES_OF_HOME);
        }
        return command.getId();
    }
}
