package com.hcmute.yourtours.factories.bed_categories;

import com.hcmute.yourtours.commands.BedCategoriesCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.bed_categories.BedCategoriesDetail;
import com.hcmute.yourtours.models.bed_categories.BedCategoriesInfo;
import com.hcmute.yourtours.repositories.BedCategoriesRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class BedCategoriesFactory
        extends BasePersistDataFactory<UUID, BedCategoriesInfo, BedCategoriesDetail, Long, BedCategoriesCommand>
        implements IBedCategoriesFactory {

    private final BedCategoriesRepository bedCategoriesRepository;

    protected BedCategoriesFactory(BedCategoriesRepository repository) {
        super(repository);
        this.bedCategoriesRepository = repository;
    }

    @Override
    @NonNull
    protected Class<BedCategoriesDetail> getDetailClass() {
        return BedCategoriesDetail.class;
    }

    @Override
    public BedCategoriesCommand createConvertToEntity(BedCategoriesDetail detail) {
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
    public void updateConvertToEntity(BedCategoriesCommand entity, BedCategoriesDetail detail) {
        entity.setName(detail.getName());
        entity.setDescription(detail.getDescription());
        entity.setStatus(detail.getStatus());
    }

    @Override
    public BedCategoriesDetail convertToDetail(BedCategoriesCommand entity) {
        return (BedCategoriesDetail) convertToInfo(entity);
    }

    @Override
    public BedCategoriesInfo convertToInfo(BedCategoriesCommand entity) {
        if (entity == null) {
            return null;
        }
        return BedCategoriesInfo.builder()
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
