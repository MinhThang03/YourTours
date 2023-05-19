package com.hcmute.yourtours.factories.amenity_categories;


import com.hcmute.yourtours.entities.AmenityCategories;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.amenity_categories.AmenityCategoryDetail;
import com.hcmute.yourtours.models.amenity_categories.AmenityCategoryInfo;
import com.hcmute.yourtours.models.amenity_categories.filter.AmenityCategoryFilter;
import com.hcmute.yourtours.repositories.AmenityCategoriesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class AmenityCategoriesFactory
        extends BasePersistDataFactory<UUID, AmenityCategoryInfo, AmenityCategoryDetail, UUID, AmenityCategories>
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
    public AmenityCategories createConvertToEntity(AmenityCategoryDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return AmenityCategories.builder()
                .name(detail.getName())
                .description(detail.getDescription())
                .status(detail.getStatus())
                .isDefault(detail.getIsDefault())
                .build();
    }

    @Override
    public void updateConvertToEntity(AmenityCategories entity, AmenityCategoryDetail detail) throws InvalidException {
        entity.setName(detail.getName());
        entity.setDescription(detail.getDescription());
        entity.setStatus(detail.getStatus());
        entity.setIsDefault(detail.getIsDefault());
    }

    @Override
    public AmenityCategoryDetail convertToDetail(AmenityCategories entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return AmenityCategoryDetail.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .isDefault(entity.getIsDefault())
                .build();
    }

    @Override
    public AmenityCategoryInfo convertToInfo(AmenityCategories entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return AmenityCategoryInfo.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .isDefault(entity.getIsDefault())
                .build();
    }

    @Override
    public Boolean existByAmenityCategoryId(UUID amenityCategoryId) {
        return amenityCategoriesRepository.existsById(amenityCategoryId);
    }

    @Override
    protected <F extends BaseFilter> Page<AmenityCategories> pageQuery(F filter, Integer number, Integer size) {
        AmenityCategoryFilter amenityCategoryFilter = (AmenityCategoryFilter) filter;

        return amenityCategoriesRepository.findPageWithFilter(
                amenityCategoryFilter.getIsDefault(),
                PageRequest.of(number, size)
        );
    }

    @Override
    protected <F extends BaseFilter> void preDelete(UUID id, F filter) throws InvalidException {

        if (id != null && !amenityCategoriesRepository.checkCanDelete(id)) {
            throw new InvalidException(YourToursErrorCode.CAN_NOT_DELETE_AMENITY_CATEGORY);
        }

        super.preDelete(id, filter);
    }
}

