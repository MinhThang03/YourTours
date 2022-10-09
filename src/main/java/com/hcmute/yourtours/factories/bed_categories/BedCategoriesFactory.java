package com.hcmute.yourtours.factories.bed_categories;

import com.hcmute.yourtours.commands.BedCategoriesCommand;
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
        extends BasePersistDataFactory<UUID, BedCategoryInfo, BedCategoryDetail, Long, BedCategoriesCommand>
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
    public BedCategoriesCommand createConvertToEntity(BedCategoryDetail detail) {
        if (detail == null) {
            return null;
        }
        return BedCategoriesCommand.builder()
                .name(detail.getName())
                .description(detail.getDescription())
                .status(detail.getStatus())
                .build();
    }

    @Override
    public void updateConvertToEntity(BedCategoriesCommand entity, BedCategoryDetail detail) {
        entity.setName(detail.getName());
        entity.setDescription(detail.getDescription());
        entity.setStatus(detail.getStatus());
    }

    @Override
    public BedCategoryDetail convertToDetail(BedCategoriesCommand entity) {
        return (BedCategoryDetail) convertToInfo(entity);
    }

    @Override
    public BedCategoryInfo convertToInfo(BedCategoriesCommand entity) {
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
        Optional<BedCategoriesCommand> optional = bedCategoriesRepository.findByBedCategoryId(id);
        if (optional.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_BED_CATEGORIES);
        }
        return optional.get().getId();
    }
}
