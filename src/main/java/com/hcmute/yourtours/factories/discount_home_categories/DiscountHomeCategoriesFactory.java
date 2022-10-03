package com.hcmute.yourtours.factories.discount_home_categories;

import com.hcmute.yourtours.commands.DiscountHomeCategoriesCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.discount_home_categories.DiscountHomeCategoriesDetail;
import com.hcmute.yourtours.models.discount_home_categories.DiscountHomeCategoriesInfo;
import com.hcmute.yourtours.repositories.DiscountHomeCategoriesRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class DiscountHomeCategoriesFactory
        extends BasePersistDataFactory<UUID, DiscountHomeCategoriesInfo, DiscountHomeCategoriesDetail, Long, DiscountHomeCategoriesCommand>
        implements IDiscountHomeCategoriesFactory {

    private final DiscountHomeCategoriesRepository discountHomeCategoriesRepository;

    protected DiscountHomeCategoriesFactory(DiscountHomeCategoriesRepository repository) {
        super(repository);
        this.discountHomeCategoriesRepository = repository;
    }

    @Override
    @NonNull
    protected Class<DiscountHomeCategoriesDetail> getDetailClass() {
        return DiscountHomeCategoriesDetail.class;
    }

    @Override
    public DiscountHomeCategoriesCommand createConvertToEntity(DiscountHomeCategoriesDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return DiscountHomeCategoriesCommand.builder()
                .name(detail.getName())
                .description(detail.getDescription())
                .status(detail.getStatus())
                .type(detail.getType())
                .numDateDefault(detail.getNumDateDefault())
                .build();
    }

    @Override
    public void updateConvertToEntity(DiscountHomeCategoriesCommand entity, DiscountHomeCategoriesDetail detail) throws InvalidException {
        entity.setName(detail.getName());
        entity.setDescription(detail.getDescription());
        entity.setStatus(detail.getStatus());
        entity.setType(detail.getType());
        entity.setNumDateDefault(detail.getNumDateDefault());
    }

    @Override
    public DiscountHomeCategoriesDetail convertToDetail(DiscountHomeCategoriesCommand entity) throws InvalidException {
        return (DiscountHomeCategoriesDetail) convertToInfo(entity);
    }

    @Override
    public DiscountHomeCategoriesInfo convertToInfo(DiscountHomeCategoriesCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return DiscountHomeCategoriesInfo.builder()
                .id(entity.getDiscountCategoryId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .type(entity.getType())
                .numDateDefault(entity.getNumDateDefault())
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        Optional<DiscountHomeCategoriesCommand> optional = discountHomeCategoriesRepository.findByDiscountCategoryId(id);
        if (optional.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_DISCOUNT_CATEGORIES);
        }
        return optional.get().getId();
    }
}
