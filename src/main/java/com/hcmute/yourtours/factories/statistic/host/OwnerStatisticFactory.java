package com.hcmute.yourtours.factories.statistic.host;

import com.hcmute.yourtours.factories.booking.IBookHomeFactory;
import com.hcmute.yourtours.factories.common.IGetUserFromTokenFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.models.statistic.host.filter.OwnerHomeStatisticFilter;
import com.hcmute.yourtours.models.statistic.host.models.HomeBookingStatistic;
import com.hcmute.yourtours.models.statistic.host.models.OwnerStatistic;
import com.hcmute.yourtours.models.statistic.host.models.RevenueStatistic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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

        Long totalBooking = iBookHomeFactory.totalBookingOfOwner(ownerId);
        Long totalBookingFinish = iBookHomeFactory.totalBookingOfOwnerFinish(ownerId);
        List<HomeBookingStatistic> homeBookingStatistics = iBookHomeFactory.getHomeBookingStatisticWithOwner(ownerId);
        List<RevenueStatistic> revenueStatistics = iBookHomeFactory.getRevenueStatisticWithOwnerAndYear(ownerId, filter.getYear());

        return OwnerStatistic.builder()
                .totalNumberOfBooking(totalBooking)
                .totalNumberOfBookingFinish(totalBookingFinish)
                .homeStatistic(homeBookingStatistics)
                .revenueStatistics(revenueStatistics)
                .build();
    }
}
