package com.hcmute.yourtours.factories.price_of_home;

import com.hcmute.yourtours.commands.PriceOfHomeCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.factories.common.IAuthorizationOwnerHomeFactory;
import com.hcmute.yourtours.factories.homes.IHomesFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.price_of_home.PriceOfHomeDetail;
import com.hcmute.yourtours.models.price_of_home.PriceOfHomeInfo;
import com.hcmute.yourtours.models.price_of_home.filter.PriceOfHomeFilter;
import com.hcmute.yourtours.models.price_of_home.models.ArrayPriceAndDayModels;
import com.hcmute.yourtours.models.price_of_home.request.GetPriceOfHomeRequest;
import com.hcmute.yourtours.models.price_of_home.request.PriceOfHomeCreateRequest;
import com.hcmute.yourtours.models.price_of_home.response.PriceOfHomeResponse;
import com.hcmute.yourtours.models.price_of_home.response.PriceOfHomeWithMonthResponse;
import com.hcmute.yourtours.repositories.PriceOfHomeRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class PriceOfHomeFactory
        extends BasePersistDataFactory<UUID, PriceOfHomeInfo, PriceOfHomeDetail, Long, PriceOfHomeCommand>
        implements IPriceOfHomeFactory {

    private final PriceOfHomeRepository priceOfHomeRepository;
    private final IHomesFactory iHomesFactory;
    private final IAuthorizationOwnerHomeFactory iAuthorizationOwnerHomeFactory;

    protected PriceOfHomeFactory(
            PriceOfHomeRepository repository,
            PriceOfHomeRepository priceOfHomeRepository,
            @Qualifier("cmsHomesFactory") IHomesFactory iHomesFactory,
            IAuthorizationOwnerHomeFactory iAuthorizationOwnerHomeFactory) {
        super(repository);
        this.priceOfHomeRepository = priceOfHomeRepository;
        this.iHomesFactory = iHomesFactory;
        this.iAuthorizationOwnerHomeFactory = iAuthorizationOwnerHomeFactory;
    }

    @Override
    @NonNull
    protected Class<PriceOfHomeDetail> getDetailClass() {
        return PriceOfHomeDetail.class;
    }

    @Override
    public PriceOfHomeCommand createConvertToEntity(PriceOfHomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return PriceOfHomeCommand.builder()
                .date(detail.getDate())
                .price(detail.getPrice())
                .homeId(detail.getHomeId())
                .build();
    }

    @Override
    public void updateConvertToEntity(PriceOfHomeCommand entity, PriceOfHomeDetail detail) throws InvalidException {
        entity.setHomeId(detail.getHomeId());
        entity.setPrice(detail.getPrice());
        entity.setPrice(detail.getPrice());
    }

    @Override
    public PriceOfHomeDetail convertToDetail(PriceOfHomeCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return PriceOfHomeDetail.builder()
                .id(entity.getPriceOfHomeId())
                .date(entity.getDate())
                .price(entity.getPrice())
                .homeId(entity.getHomeId())
                .build();
    }

    @Override
    public PriceOfHomeInfo convertToInfo(PriceOfHomeCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return PriceOfHomeInfo.builder()
                .id(entity.getPriceOfHomeId())
                .date(entity.getDate())
                .price(entity.getPrice())
                .homeId(entity.getHomeId())
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        PriceOfHomeCommand command = priceOfHomeRepository.findByPriceOfHomeId(id).orElse(null);
        if (command == null) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_PRICE_OF_HOME);
        }
        return command.getId();
    }

    @Override
    protected void preCreate(PriceOfHomeDetail detail) throws InvalidException {
        List<PriceOfHomeCommand> listDelete = priceOfHomeRepository.findAllByHomeIdAndDate(detail.getHomeId(), detail.getDate());
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
                            .cost(detail.getPrice())
                            .build()
            );

            date = date.plusDays(1);
        }


        return PriceOfHomeResponse.builder()
                .totalCost(total)
                .detail(priceDetail)
                .build();
    }


    private PriceOfHomeDetail findByHomeIdAndDate(UUID homeId, LocalDate date) throws InvalidException {
        Optional<PriceOfHomeCommand> optional = priceOfHomeRepository.findByHomeIdAndDate(homeId, date);
        if (optional.isPresent()) {
            return convertToDetail(optional.get());
        }

        return PriceOfHomeDetail.builder()
                .date(date)
                .homeId(homeId)
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
