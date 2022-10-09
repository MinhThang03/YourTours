package com.hcmute.yourtours.factories.amenities;

import com.hcmute.yourtours.commands.AmenitiesCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.factories.amenity_categories.IAmenityCategoriesFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.amenities.AmenityDetail;
import com.hcmute.yourtours.models.amenities.AmenityInfo;
import com.hcmute.yourtours.models.amenity_categories.AmenityCategoryDetail;
import com.hcmute.yourtours.repositories.AmenitiesRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AmenitiesFactory
        extends BasePersistDataFactory<UUID, AmenityInfo, AmenityDetail, Long, AmenitiesCommand>
        implements IAmenitiesFactory {

    private final AmenitiesRepository amenitiesRepository;
    private final IAmenityCategoriesFactory iAmenityCategoriesFactory;

    protected AmenitiesFactory(AmenitiesRepository repository,
                               IAmenityCategoriesFactory iAmenityCategoriesFactory) {
        super(repository);
        this.amenitiesRepository = repository;
        this.iAmenityCategoriesFactory = iAmenityCategoriesFactory;
    }

    @Override
    @NonNull
    protected Class<AmenityDetail> getDetailClass() {
        return AmenityDetail.class;
    }

    @Override
    protected void preCreate(AmenityDetail detail) throws InvalidException {
        if (!iAmenityCategoriesFactory.existByAmenityCategoryId(detail.getCategoryId())) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_AMENITY_CATEGORIES_AMENITY_FACTORY);
        }
    }

    @Override
    public AmenitiesCommand createConvertToEntity(AmenityDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return AmenitiesCommand.builder()
                .name(detail.getName())
                .description(detail.getDescription())
                .status(detail.getStatus())
                .categoryId(detail.getCategoryId())
                .build();
    }

    @Override
    protected void preUpdate(UUID id, AmenityDetail detail) throws InvalidException {
        if (!iAmenityCategoriesFactory.existByAmenityCategoryId(detail.getCategoryId())) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_AMENITY_CATEGORIES_AMENITY_FACTORY);
        }
    }

    @Override
    public void updateConvertToEntity(AmenitiesCommand entity, AmenityDetail detail) throws InvalidException {
        entity.setName(detail.getName());
        entity.setDescription(detail.getDescription());
        entity.setStatus(detail.getStatus());
        entity.setCategoryId(detail.getCategoryId());
    }

    @Override
    public AmenityDetail convertToDetail(AmenitiesCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        AmenityCategoryDetail category = iAmenityCategoriesFactory.getDetailModel(entity.getCategoryId(), null);
        return AmenityDetail.builder()
                .id(entity.getAmenityId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .category(category)
                .categoryName(category.getName())
                .build();
    }

    @Override
    public AmenityInfo convertToInfo(AmenitiesCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return AmenityInfo.builder()
                .id(entity.getAmenityId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .categoryName(iAmenityCategoriesFactory.getDetailModel(entity.getCategoryId(), null).getName())
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        Optional<AmenitiesCommand> optional = amenitiesRepository.findByAmenityId(id);
        if (optional.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_AMENITY);
        }
        return optional.get().getId();
    }
}
