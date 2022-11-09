package com.hcmute.yourtours.factories.discount_of_home;

import com.hcmute.yourtours.commands.DiscountOfHomeCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.factories.discount_home_categories.IDiscountHomeCategoriesFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.discount_home_categories.DiscountHomeCategoryDetail;
import com.hcmute.yourtours.models.discount_of_home.DiscountOfHomeDetail;
import com.hcmute.yourtours.models.discount_of_home.DiscountOfHomeInfo;
import com.hcmute.yourtours.models.discount_of_home.models.DiscountOfHomeViewModel;
import com.hcmute.yourtours.repositories.DiscountOfHomeRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class DiscountOfHomeFactory
        extends BasePersistDataFactory<UUID, DiscountOfHomeInfo, DiscountOfHomeDetail, Long, DiscountOfHomeCommand>
        implements IDiscountOfHomeFactory {

    protected final DiscountOfHomeRepository discountOfHomeRepository;
    private final IDiscountHomeCategoriesFactory iDiscountHomeCategoriesFactory;

    protected DiscountOfHomeFactory(DiscountOfHomeRepository repository,
                                    IDiscountHomeCategoriesFactory iDiscountHomeCategoriesFactory) {
        super(repository);
        this.discountOfHomeRepository = repository;
        this.iDiscountHomeCategoriesFactory = iDiscountHomeCategoriesFactory;
    }

    @Override
    @NonNull
    protected Class<DiscountOfHomeDetail> getDetailClass() {
        return DiscountOfHomeDetail.class;
    }

    @Override
    public DiscountOfHomeCommand createConvertToEntity(DiscountOfHomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return DiscountOfHomeCommand.builder()
                .percent(detail.getPercent())
                .numberDateStay(detail.getNumberDateStay())
                .numberMonthAdvance(detail.getNumberMonthAdvance())
                .categoryId(detail.getCategoryId())
                .homeId(detail.getHomeId())
                .build();
    }

    @Override
    public void updateConvertToEntity(DiscountOfHomeCommand entity, DiscountOfHomeDetail detail) throws InvalidException {
        entity.setPercent(detail.getPercent());
        entity.setNumberDateStay(detail.getNumberDateStay());
        entity.setNumberMonthAdvance(detail.getNumberMonthAdvance());
        entity.setCategoryId(detail.getCategoryId());
        entity.setHomeId(detail.getHomeId());
    }

    @Override
    public DiscountOfHomeDetail convertToDetail(DiscountOfHomeCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return DiscountOfHomeDetail.builder()
                .id(entity.getDiscountOfHomeId())
                .percent(entity.getPercent())
                .numberDateStay(entity.getNumberDateStay())
                .numberMonthAdvance(entity.getNumberMonthAdvance())
                .categoryId(entity.getCategoryId())
                .homeId(entity.getHomeId())
                .build();
    }

    @Override
    public DiscountOfHomeInfo convertToInfo(DiscountOfHomeCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return DiscountOfHomeInfo.builder()
                .id(entity.getDiscountOfHomeId())
                .percent(entity.getPercent())
                .numberDateStay(entity.getNumberDateStay())
                .numberMonthAdvance(entity.getNumberMonthAdvance())
                .categoryId(entity.getCategoryId())
                .homeId(entity.getHomeId())
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        DiscountOfHomeCommand command = discountOfHomeRepository.findByDiscountOfHomeId(id).orElse(null);
        if (command == null) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_DISCOUNTS_OF_HOME);
        }
        return command.getId();
    }

    @Override
    protected void preCreate(DiscountOfHomeDetail detail) throws InvalidException {
        iDiscountHomeCategoriesFactory.checkExistsByDiscountCategoryId(detail.getCategoryId());
        discountOfHomeRepository.deleteAllByHomeIdAndCategoryId(detail.getHomeId(), detail.getCategoryId());
    }

    @Override
    public List<DiscountOfHomeViewModel> getDiscountsOfHomeView(UUID homeId) throws InvalidException {
        List<DiscountHomeCategoryDetail> categories = iDiscountHomeCategoriesFactory.getDiscountCategoriesActive();
        List<DiscountOfHomeViewModel> result = new ArrayList<>();
        for (DiscountHomeCategoryDetail category : categories) {
            Optional<DiscountOfHomeCommand> optional = discountOfHomeRepository.findByHomeIdAndCategoryId(homeId, category.getId());
            result.add
                    (
                            DiscountOfHomeViewModel
                                    .builder()
                                    .category(category)
                                    .config(optional.isEmpty() ? null : convertToDetail(optional.get()))
                                    .build()
                    );
        }
        return result;
    }
}
