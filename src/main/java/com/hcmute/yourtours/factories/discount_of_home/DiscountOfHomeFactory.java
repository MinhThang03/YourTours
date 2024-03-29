package com.hcmute.yourtours.factories.discount_of_home;

import com.hcmute.yourtours.entities.DiscountOfHome;
import com.hcmute.yourtours.factories.discount_home_categories.IDiscountHomeCategoriesFactory;
import com.hcmute.yourtours.factories.websocket.IWebSocketFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.discount_home_categories.DiscountHomeCategoryDetail;
import com.hcmute.yourtours.models.discount_of_home.DiscountOfHomeDetail;
import com.hcmute.yourtours.models.discount_of_home.DiscountOfHomeInfo;
import com.hcmute.yourtours.models.discount_of_home.models.CreateListDiscountOfHomeModel;
import com.hcmute.yourtours.models.discount_of_home.models.DiscountOfHomeViewModel;
import com.hcmute.yourtours.models.discount_of_home.projections.NotificationDiscountProjection;
import com.hcmute.yourtours.repositories.DiscountOfHomeRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class DiscountOfHomeFactory
        extends BasePersistDataFactory<UUID, DiscountOfHomeInfo, DiscountOfHomeDetail, UUID, DiscountOfHome>
        implements IDiscountOfHomeFactory {

    protected final DiscountOfHomeRepository discountOfHomeRepository;
    private final IDiscountHomeCategoriesFactory iDiscountHomeCategoriesFactory;
    private final IWebSocketFactory iWebSocketFactory;

    protected DiscountOfHomeFactory
            (
                    DiscountOfHomeRepository repository,
                    IDiscountHomeCategoriesFactory iDiscountHomeCategoriesFactory,
                    IWebSocketFactory iWebSocketFactory
            ) {
        super(repository);
        this.discountOfHomeRepository = repository;
        this.iDiscountHomeCategoriesFactory = iDiscountHomeCategoriesFactory;
        this.iWebSocketFactory = iWebSocketFactory;
    }

    @Override
    @NonNull
    protected Class<DiscountOfHomeDetail> getDetailClass() {
        return DiscountOfHomeDetail.class;
    }

    @Override
    public DiscountOfHome createConvertToEntity(DiscountOfHomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return DiscountOfHome.builder()
                .percent(detail.getPercent())
                .numberDateStay(detail.getNumberDateStay())
                .numberMonthAdvance(detail.getNumberMonthAdvance())
                .categoryId(detail.getCategoryId())
                .homeId(detail.getHomeId())
                .dateStart(detail.getDateStart())
                .dateEnd(detail.getDateEnd())
                .build();
    }

    @Override
    public void updateConvertToEntity(DiscountOfHome entity, DiscountOfHomeDetail detail) throws InvalidException {
        entity.setPercent(detail.getPercent());
        entity.setNumberDateStay(detail.getNumberDateStay());
        entity.setNumberMonthAdvance(detail.getNumberMonthAdvance());
        entity.setCategoryId(detail.getCategoryId());
        entity.setHomeId(detail.getHomeId());
        entity.setDateStart(detail.getDateStart());
        entity.setDateEnd(detail.getDateEnd());
    }

    @Override
    public DiscountOfHomeDetail convertToDetail(DiscountOfHome entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return DiscountOfHomeDetail.builder()
                .id(entity.getId())
                .percent(entity.getPercent())
                .numberDateStay(entity.getNumberDateStay())
                .numberMonthAdvance(entity.getNumberMonthAdvance())
                .categoryId(entity.getCategoryId())
                .homeId(entity.getHomeId())
                .dateStart(entity.getDateStart())
                .dateEnd(entity.getDateEnd())
                .build();
    }

    @Override
    public DiscountOfHomeInfo convertToInfo(DiscountOfHome entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return DiscountOfHomeInfo.builder()
                .id(entity.getId())
                .percent(entity.getPercent())
                .numberDateStay(entity.getNumberDateStay())
                .numberMonthAdvance(entity.getNumberMonthAdvance())
                .categoryId(entity.getCategoryId())
                .homeId(entity.getHomeId())
                .dateStart(entity.getDateStart())
                .dateEnd(entity.getDateEnd())
                .build();
    }


    @Override
    protected void preCreate(DiscountOfHomeDetail detail) throws InvalidException {
        iDiscountHomeCategoriesFactory.checkExistsByDiscountCategoryId(detail.getCategoryId());

        List<DiscountOfHome> listDelete = discountOfHomeRepository.findAllByHomeIdAndCategoryId(detail.getHomeId(), detail.getCategoryId());
        repository.deleteAll(listDelete);
    }

    @Override
    public List<DiscountOfHomeViewModel> getDiscountsOfHomeView(UUID homeId, LocalDate dateBooking) throws InvalidException {
        List<DiscountHomeCategoryDetail> categories = iDiscountHomeCategoriesFactory.getDiscountCategoriesActive();
        List<DiscountOfHomeViewModel> result = new ArrayList<>();
        for (DiscountHomeCategoryDetail category : categories) {
            Optional<DiscountOfHome> optional = discountOfHomeRepository
                    .findByHomeIdAndCategoryIdAndDate(homeId, category.getId(), dateBooking);
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

    @Override
    public List<DiscountOfHomeViewModel> getDiscountsOfHomeView(UUID homeId) throws InvalidException {
        List<DiscountHomeCategoryDetail> categories = iDiscountHomeCategoriesFactory.getDiscountCategoriesActive();
        List<DiscountOfHomeViewModel> result = new ArrayList<>();
        for (DiscountHomeCategoryDetail category : categories) {
            Optional<DiscountOfHome> optional = discountOfHomeRepository.findByHomeIdAndCategoryId(homeId, category.getId());
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

    @Override
    public SuccessResponse createListDiscountOfHome(CreateListDiscountOfHomeModel request) throws InvalidException {
        if (request != null && request.getListDiscountOfHomeDetail() != null) {
            for (DiscountOfHomeDetail item : request.getListDiscountOfHomeDetail()) {
                DiscountOfHomeDetail detail = createModel(item);
                List<NotificationDiscountProjection> projections = discountOfHomeRepository
                        .getListNotificationDiscount(detail.getId());
                iWebSocketFactory.sendDiscountNotificationMessage(projections);
            }
        }
        return SuccessResponse.builder()
                .success(true)
                .build();
    }
}
