package com.hcmute.yourtours.factories.discount_home_categories;

import com.hcmute.yourtours.entities.DiscountHomeCategoriesCommand;
import com.hcmute.yourtours.enums.CommonStatusEnum;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.discount_home_categories.DiscountHomeCategoryDetail;
import com.hcmute.yourtours.models.discount_home_categories.DiscountHomeCategoryInfo;
import com.hcmute.yourtours.repositories.DiscountHomeCategoriesRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class DiscountHomeCategoriesFactory
        extends BasePersistDataFactory<UUID, DiscountHomeCategoryInfo, DiscountHomeCategoryDetail, Long, DiscountHomeCategoriesCommand>
        implements IDiscountHomeCategoriesFactory {

    private final DiscountHomeCategoriesRepository discountHomeCategoriesRepository;

    protected DiscountHomeCategoriesFactory(DiscountHomeCategoriesRepository repository) {
        super(repository);
        this.discountHomeCategoriesRepository = repository;
    }

    @Override
    @NonNull
    protected Class<DiscountHomeCategoryDetail> getDetailClass() {
        return DiscountHomeCategoryDetail.class;
    }

    @Override
    public DiscountHomeCategoriesCommand createConvertToEntity(DiscountHomeCategoryDetail detail) throws InvalidException {
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
    public void updateConvertToEntity(DiscountHomeCategoriesCommand entity, DiscountHomeCategoryDetail detail) throws InvalidException {
        entity.setName(detail.getName());
        entity.setDescription(detail.getDescription());
        entity.setStatus(detail.getStatus());
        entity.setType(detail.getType());
        entity.setNumDateDefault(detail.getNumDateDefault());
    }

    @Override
    public DiscountHomeCategoryDetail convertToDetail(DiscountHomeCategoriesCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return DiscountHomeCategoryDetail.builder()
                .id(entity.getDiscountCategoryId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .type(entity.getType())
                .numDateDefault(entity.getNumDateDefault())
                .build();
    }

    @Override
    public DiscountHomeCategoryInfo convertToInfo(DiscountHomeCategoriesCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return DiscountHomeCategoryInfo.builder()
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

    @Override
    public void checkExistsByDiscountCategoryId(UUID categoryId) throws InvalidException {
        Optional<DiscountHomeCategoriesCommand> optional = discountHomeCategoriesRepository.findByDiscountCategoryId(categoryId);
        if (optional.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_DISCOUNT_CATEGORIES);
        }
    }

    @Override
    public List<DiscountHomeCategoryDetail> getDiscountCategoriesActive() throws InvalidException {
        List<DiscountHomeCategoriesCommand> commands = discountHomeCategoriesRepository.findAllByStatus(CommonStatusEnum.ACTIVE);
        List<DiscountHomeCategoryDetail> result = new ArrayList<>();
        for (DiscountHomeCategoriesCommand command : commands) {
            result.add(convertToDetail(command));
        }
        return result;
    }
}
