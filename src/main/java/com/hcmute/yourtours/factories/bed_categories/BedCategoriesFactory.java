package com.hcmute.yourtours.factories.bed_categories;

import com.hcmute.yourtours.entities.BedCategories;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.bed_categories.BedCategoryDetail;
import com.hcmute.yourtours.models.bed_categories.BedCategoryInfo;
import com.hcmute.yourtours.repositories.BedCategoriesRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class BedCategoriesFactory
        extends BasePersistDataFactory<UUID, BedCategoryInfo, BedCategoryDetail, Long, BedCategories>
        implements IBedCategoriesFactory {

    private final BedCategoriesRepository bedCategoriesRepository;

    protected BedCategoriesFactory(BedCategoriesRepository repository) {
        super(repository);
        this.bedCategoriesRepository = repository;
    }

    @Override
    @NonNull
    protected Class<BedCategoryDetail> getDetailClass() {
        return BedCategoryDetail.class;
    }

    @Override
    public BedCategories createConvertToEntity(BedCategoryDetail detail) {
        if (detail == null) {
            return null;
        }
        return BedCategories.builder()
                .name(detail.getName())
                .description(detail.getDescription())
                .status(detail.getStatus())
                .build();
    }

    @Override
    public void updateConvertToEntity(BedCategories entity, BedCategoryDetail detail) {
        entity.setName(detail.getName());
        entity.setDescription(detail.getDescription());
        entity.setStatus(detail.getStatus());
    }

    @Override
    public BedCategoryDetail convertToDetail(BedCategories entity) {
        if (entity == null) {
            return null;
        }
        return BedCategoryDetail.builder()
                .id(entity.getBedCategoryId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .build();
    }

    @Override
    public BedCategoryInfo convertToInfo(BedCategories entity) {
        if (entity == null) {
            return null;
        }
        return BedCategoryInfo.builder()
                .id(entity.getBedCategoryId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        Optional<BedCategories> optional = bedCategoriesRepository.findByBedCategoryId(id);
        if (optional.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_BED_CATEGORIES);
        }
        return optional.get().getId();
    }

    @Override
    public void checkExistsByBedCategoryId(UUID bedCategoryId) throws InvalidException {
        Optional<BedCategories> optional = bedCategoriesRepository.findByBedCategoryId(bedCategoryId);
        if (optional.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_BED_CATEGORIES);
        }
    }
}
