package com.hcmute.yourtours.factories.amenity_categories;


import com.hcmute.yourtours.commands.AmenityCategoriesCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.amenity_categories.AmenityCategoryDetail;
import com.hcmute.yourtours.models.amenity_categories.AmenityCategoryInfo;
import com.hcmute.yourtours.repositories.AmenityCategoriesRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AmenityCategoriesFactory
        extends BasePersistDataFactory<UUID, AmenityCategoryInfo, AmenityCategoryDetail, Long, AmenityCategoriesCommand>
        implements IAmenityCategoriesFactory {

    private final AmenityCategoriesRepository amenityCategoriesRepository;

    protected AmenityCategoriesFactory(AmenityCategoriesRepository repository) {
        super(repository);
        this.amenityCategoriesRepository = repository;
    }

    @Override
    @NonNull
    protected Class<AmenityCategoryDetail> getDetailClass() {
        return AmenityCategoryDetail.class;
    }

    @Override
    public AmenityCategoriesCommand createConvertToEntity(AmenityCategoryDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return AmenityCategoriesCommand.builder()
                .name(detail.getName())
                .description(detail.getDescription())
                .status(detail.getStatus())
                .build();
    }

    @Override
    public void updateConvertToEntity(AmenityCategoriesCommand entity, AmenityCategoryDetail detail) throws InvalidException {
        entity.setName(detail.getName());
        entity.setDescription(detail.getDescription());
        entity.setStatus(detail.getStatus());
    }

    @Override
    public AmenityCategoryDetail convertToDetail(AmenityCategoriesCommand entity) throws InvalidException {
        return (AmenityCategoryDetail) convertToInfo(entity);
    }

    @Override
    public AmenityCategoryInfo convertToInfo(AmenityCategoriesCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return AmenityCategoryInfo.builder()
                .id(entity.getAmenityCategoryId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        Optional<AmenityCategoriesCommand> optional = amenityCategoriesRepository.findByAmenityCategoryId(id);
        if (optional.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_AMENITY_CATEGORIES);
        }
        return optional.get().getId();
    }

    @Override
    public Boolean existByAmenityCategoryId(UUID amenityCategoryId) {
        return amenityCategoriesRepository.existsByAmenityCategoryId(amenityCategoryId);
    }
}

