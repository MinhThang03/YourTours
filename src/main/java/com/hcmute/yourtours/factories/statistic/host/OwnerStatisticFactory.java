package com.hcmute.yourtours.factories.statistic.host;

import com.hcmute.yourtours.factories.booking.IBookHomeFactory;
import com.hcmute.yourtours.factories.common.IGetUserFromTokenFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.models.statistic.common.RevenueStatistic;
import com.hcmute.yourtours.models.statistic.host.filter.OwnerHomeStatisticFilter;
import com.hcmute.yourtours.models.statistic.host.filter.OwnerHomeStatisticMonthFilter;
import com.hcmute.yourtours.models.statistic.host.models.HomeBookingStatistic;
import com.hcmute.yourtours.models.statistic.host.models.OwnerHomeStatisticMonth;
import com.hcmute.yourtours.models.statistic.host.models.OwnerStatistic;
import com.hcmute.yourtours.models.statistic.host.projections.OwnerHomeStatisticProjection;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OwnerStatisticFactory implements IOwnerStatisticFactory {

    private final IBookHomeFactory iBookHomeFactory;
    private final IGetUserFromTokenFactory iGetUserFromTokenFactory;

    public OwnerStatisticFactory
            (
                    @Qualifier("bookHomeFactory") IBookHomeFactory iBookHomeFactory,
                    IGetUserFromTokenFactory iGetUserFromTokenFactory
            ) {
        this.iBookHomeFactory = iBookHomeFactory;
        this.iGetUserFromTokenFactory = iGetUserFromTokenFactory;
    }

    @Override
    public OwnerStatistic statistic(OwnerHomeStatisticFilter filter) throws InvalidException {
        if (filter.getYear() == null) {
            filter.setYear(LocalDate.now().getYear());
        }

        UUID ownerId = iGetUserFromTokenFactory.checkUnAuthorization();

        Long totalBooking = iBookHomeFactory.totalBookingOfOwner(ownerId, filter.getYear());
        Long totalBookingFinish = iBookHomeFactory.totalBookingOfOwnerFinish(ownerId, filter.getYear());
        List<HomeBookingStatistic> homeBookingStatistics = iBookHomeFactory.getHomeBookingStatisticWithOwner(ownerId, filter.getYear());
        List<RevenueStatistic> revenueStatistics = iBookHomeFactory.getRevenueStatisticWithOwnerAndYear(ownerId, filter.getYear());

        return OwnerStatistic.builder()
                .totalNumberOfBooking(totalBooking)
                .totalNumberOfBookingFinish(totalBookingFinish)
                .homeStatistic(homeBookingStatistics)
                .revenueStatistics(revenueStatistics)
                .build();
    }


    @Override
    public BasePagingResponse<OwnerHomeStatisticMonth> ownerHomeStatistic(OwnerHomeStatisticMonthFilter filter, Integer number, Integer size) throws InvalidException {

        UUID userId = iGetUserFromTokenFactory.checkUnAuthorization();

        Integer month = filter.getMonth() == null ? LocalDate.now().getMonthValue() : filter.getMonth();
        Integer year = filter.getYear() == null ? LocalDate.now().getYear() : filter.getYear();

        Page<OwnerHomeStatisticProjection> projections = iBookHomeFactory
                .getStatisticMonthForOwner(userId, month, year, PageRequest.of(number, size));

        List<OwnerHomeStatisticMonth> result = projections.getContent().stream().map(
                item -> OwnerHomeStatisticMonth.builder()
                        .homeId(item.getHomeId())
                        .homeName(item.getHomeName())
                        .numberOfBooking(item.getNumberOfBooking())
                        .numberOfView(item.getNumberOfView())
                        .revenue(item.getRevenue())
                        .averageRate(item.getNumberOfEvaluate() == 0
                                ? 0
                                : item.getPoint() / item.getNumberOfEvaluate())
                        .numberOfEvaluate(item.getNumberOfEvaluate())
                        .reservationRate(item.getNumberOfView() == 0
                                ? 100
                                : item.getNumberOfBooking() * 1.0 / item.getNumberOfView())
                        .build()
        ).collect(Collectors.toList());

        return new BasePagingResponse<>(
                result,
                number,
                size,
                projections.getTotalElements());
    }

}
