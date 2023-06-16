package com.hcmute.yourtours.factories.price_of_home;

import com.hcmute.yourtours.entities.PriceOfHome;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.factories.common.IAuthorizationOwnerHomeFactory;
import com.hcmute.yourtours.factories.discount_of_home.IDiscountOfHomeFactory;
import com.hcmute.yourtours.factories.homes.IHomesFactory;
import com.hcmute.yourtours.factories.surcharges_of_home.ISurchargeOfHomeFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.discount_of_home.models.DiscountOfHomeViewModel;
import com.hcmute.yourtours.models.price_of_home.PriceOfHomeDetail;
import com.hcmute.yourtours.models.price_of_home.PriceOfHomeInfo;
import com.hcmute.yourtours.models.price_of_home.filter.PriceOfHomeFilter;
import com.hcmute.yourtours.models.price_of_home.models.ArrayPriceAndDayModels;
import com.hcmute.yourtours.models.price_of_home.request.GetPriceOfHomeRequest;
import com.hcmute.yourtours.models.price_of_home.request.PriceOfHomeCreateRequest;
import com.hcmute.yourtours.models.price_of_home.response.PriceOfHomeResponse;
import com.hcmute.yourtours.models.price_of_home.response.PriceOfHomeWithMonthResponse;
import com.hcmute.yourtours.models.surcharges_of_home.models.SurchargeHomeViewModel;
import com.hcmute.yourtours.repositories.PriceOfHomeRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class PriceOfHomeFactory
        extends BasePersistDataFactory<UUID, PriceOfHomeInfo, PriceOfHomeDetail, UUID, PriceOfHome>
        implements IPriceOfHomeFactory {

    private final PriceOfHomeRepository priceOfHomeRepository;
    private final IHomesFactory iHomesFactory;
    private final IAuthorizationOwnerHomeFactory iAuthorizationOwnerHomeFactory;
    private final ISurchargeOfHomeFactory iSurchargeOfHomeFactory;
    private final IDiscountOfHomeFactory iDiscountOfHomeFactory;

    protected PriceOfHomeFactory(
            PriceOfHomeRepository repository,
            PriceOfHomeRepository priceOfHomeRepository,
            @Qualifier("cmsHomesFactory") IHomesFactory iHomesFactory,
            IAuthorizationOwnerHomeFactory iAuthorizationOwnerHomeFactory,
            ISurchargeOfHomeFactory iSurchargeOfHomeFactory,
            IDiscountOfHomeFactory iDiscountOfHomeFactory
    ) {
        super(repository);
        this.priceOfHomeRepository = priceOfHomeRepository;
        this.iHomesFactory = iHomesFactory;
        this.iAuthorizationOwnerHomeFactory = iAuthorizationOwnerHomeFactory;
        this.iSurchargeOfHomeFactory = iSurchargeOfHomeFactory;
        this.iDiscountOfHomeFactory = iDiscountOfHomeFactory;
    }

    @Override
    @NonNull
    protected Class<PriceOfHomeDetail> getDetailClass() {
        return PriceOfHomeDetail.class;
    }

    @Override
    public PriceOfHome createConvertToEntity(PriceOfHomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return PriceOfHome.builder()
                .date(detail.getDate())
                .price(detail.getPrice())
                .homeId(detail.getHomeId())
                .build();
    }

    @Override
    public void updateConvertToEntity(PriceOfHome entity, PriceOfHomeDetail detail) throws InvalidException {
        entity.setHomeId(detail.getHomeId());
        entity.setPrice(detail.getPrice());
        entity.setPrice(detail.getPrice());
    }

    @Override
    public PriceOfHomeDetail convertToDetail(PriceOfHome entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return PriceOfHomeDetail.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .price(entity.getPrice())
                .homeId(entity.getHomeId())
                .build();
    }

    @Override
    public PriceOfHomeInfo convertToInfo(PriceOfHome entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return PriceOfHomeInfo.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .price(entity.getPrice())
                .homeId(entity.getHomeId())
                .build();
    }

    @Override
    protected void preCreate(PriceOfHomeDetail detail) throws InvalidException {
        LocalDate now = LocalDate.now();
        if (detail.getDate().isBefore(now)) {
            throw new InvalidException(YourToursErrorCode.INPUT_DAY_IS_BEFORE);
        }

        List<PriceOfHome> listDelete = priceOfHomeRepository.findAllByHomeIdAndDate(detail.getHomeId(), detail.getDate());
        if (!listDelete.isEmpty()) {
            repository.deleteAll(listDelete);
        }
    }

    @Override
    public SuccessResponse createWithHomeId(PriceOfHomeCreateRequest request) throws InvalidException {
        iAuthorizationOwnerHomeFactory.checkOwnerOfHome(request.getHomeId());

        if (request.getDateEnd().isBefore(request.getDateStart())) {
            throw new InvalidException(YourToursErrorCode.DAY_END_IS_BEFORE_DAY_START);
        }

        LocalDate date = request.getDateStart();
        while (!date.isAfter(request.getDateEnd())) {
            PriceOfHomeDetail detail = PriceOfHomeDetail.builder()
                    .homeId(request.getHomeId())
                    .date(date)
                    .price(request.getPrice())
                    .build();

            createModel(detail);
            date = date.plusDays(1);
        }

        return SuccessResponse.builder()
                .success(true)
                .build();
    }

    @Override
    public PriceOfHomeWithMonthResponse getByHomeIdAndMonth(PriceOfHomeFilter filter) throws InvalidException {
        List<LocalDate> daysOfMonth = getListDayOfMonth(filter.getMonth(), filter.getYear());

        List<PriceOfHomeDetail> prices = new ArrayList<>();
        for (LocalDate date : daysOfMonth) {
            prices.add(findByHomeIdAndDate(filter.getHomeId(), date));
        }

        return PriceOfHomeWithMonthResponse.builder()
                .homeId(filter.getHomeId())
                .month(filter.getMonth())
                .prices(prices)
                .build();
    }

    @Override
    public PriceOfHomeResponse getCostBetweenDay(GetPriceOfHomeRequest request) throws InvalidException {
        if (request.getDateFrom().isAfter(request.getDateTo())) {
            throw new InvalidException(YourToursErrorCode.INPUT_DAY_NOT_VALID);
        }
        double total = 0.0;
        LocalDate date = request.getDateFrom();

        List<ArrayPriceAndDayModels> priceDetail = new ArrayList<>();

        while (!date.isAfter(request.getDateTo())) {
            PriceOfHomeDetail detail = findByHomeIdAndDate(request.getHomeId(), date);
            total += detail.getPrice();

            priceDetail.add(
                    ArrayPriceAndDayModels.builder()
                            .day(date)
                            .isEspecially(detail.isEspecially())
                            .cost(detail.getPrice())
                            .build()
            );

            date = date.plusDays(1);
        }

        double surchargeFee = 0.0;

        List<SurchargeHomeViewModel> surcharges = iSurchargeOfHomeFactory.getListSurchargeOfHome(request.getHomeId());
        for (SurchargeHomeViewModel item : surcharges) {
            surchargeFee += item.getCost();
        }

        Long numberOfDay = Duration.between(LocalDateTime.of(request.getDateFrom(), LocalTime.MIDNIGHT), LocalDateTime.of(request.getDateTo(), LocalTime.MIDNIGHT)).toDays() + 1;
        List<DiscountOfHomeViewModel> discounts = iDiscountOfHomeFactory.getDiscountsOfHomeView(request.getHomeId(), request.getDateFrom());
        Double percent = null;
        String discountOfName = null;
        for (DiscountOfHomeViewModel discount : discounts) {
            if (discount.getConfig() == null) {
                continue;
            }

            Double temp = null;
            if (discount.getCategory().getNumDateDefault() <= numberOfDay) {
                temp = discount.getConfig().getPercent();
            }

            if (percent == null || (temp != null && percent < temp)) {
                percent = temp;
                discountOfName = discount.getCategory().getName();
            }
        }

        double totalCost;

        if (percent != null) {
            totalCost = Math.round((total + surchargeFee) * ((100 - percent) / 100));
        } else {
            totalCost = total + surchargeFee;
        }

        return PriceOfHomeResponse.builder()
                .totalCost(total)
                .totalCostWithNoDiscount(total + surchargeFee)
                .totalCostWithSurcharge(totalCost)
                .surchargeCost(surchargeFee)
                .discountName(discountOfName)
                .percent(percent)
                .detail(priceDetail)
                .build();
    }


    private PriceOfHomeDetail findByHomeIdAndDate(UUID homeId, LocalDate date) throws InvalidException {
        Optional<PriceOfHome> optional = priceOfHomeRepository.findByHomeIdAndDate(homeId, date);
        if (optional.isPresent()) {
            PriceOfHomeDetail detail = convertToDetail(optional.get());
            detail.setEspecially(true);
            return detail;
        }

        return PriceOfHomeDetail.builder()
                .date(date)
                .homeId(homeId)
                .isEspecially(false)
                .price(iHomesFactory.getDetailModel(homeId, null).getCostPerNightDefault())
                .build();
    }

    private List<LocalDate> getListDayOfMonth(Integer month, Integer year) {
        YearMonth ym = YearMonth.of(year, Month.of(month));
        LocalDate firstOfMonth = ym.atDay(1);
        LocalDate firstOfFollowingMonth = ym.plusMonths(1).atDay(1);
        return firstOfMonth.datesUntil(firstOfFollowingMonth).collect(Collectors.toList());
    }


}
