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
                .isDefault(detail.getIsDefault())
                .build();
    }

    @Override
    public void updateConvertToEntity(AmenityCategoriesCommand entity, AmenityCategoryDetail detail) throws InvalidException {
        entity.setName(detail.getName());
        entity.setDescription(detail.getDescription());
        entity.setStatus(detail.getStatus());
        entity.setIsDefault(detail.getIsDefault());
    }

    @Override
    public AmenityCategoryDetail convertToDetail(AmenityCategoriesCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return AmenityCategoryDetail.builder()
                .id(entity.getAmenityCategoryId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .isDefault(entity.getIsDefault())
                .build();
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
                .isDefault(entity.getIsDefault())
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

    @Override
    protected void preCreate(AmenityCategoryDetail detail) throws InvalidException {
        resetDefaultCategory(detail);
    }

    @Override
    protected void preUpdate(UUID id, AmenityCategoryDetail detail) throws InvalidException {
        resetDefaultCategory(detail);
    }


    private void resetDefaultCategory(AmenityCategoryDetail detail) throws InvalidException {
        if (Boolean.TRUE.equals(detail.getIsDefault())) {
            AmenityCategoriesCommand command = amenityCategoriesRepository.findByIsDefault(true).orElse(null);
            if (command != null) {
                AmenityCategoryDetail defaultDetail = convertToDetail(command);
                defaultDetail.setIsDefault(false);
                updateModel(defaultDetail.getId(), defaultDetail);
            }
        }
    }
}

