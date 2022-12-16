package com.hcmute.yourtours.factories.statistic.admin;

import com.hcmute.yourtours.factories.booking.IBookHomeFactory;
import com.hcmute.yourtours.factories.user.IUserFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.models.statistic.admin.filter.AdminHomeStatisticFilter;
import com.hcmute.yourtours.models.statistic.admin.models.AdminStatistic;
import com.hcmute.yourtours.models.statistic.admin.projections.StatisticCountProjections;
import com.hcmute.yourtours.models.statistic.common.RevenueStatistic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminStatisticFactory implements IAdminStatisticFactory {

    private final IUserFactory iUserFactory;
    private final IBookHomeFactory iBookHomeFactory;

    public AdminStatisticFactory
            (
                    IUserFactory iUserFactory,
                    @Qualifier("bookHomeFactory") IBookHomeFactory iBookHomeFactory
            ) {
        this.iUserFactory = iUserFactory;
        this.iBookHomeFactory = iBookHomeFactory;
    }

    @Override
    public AdminStatistic statistic(AdminHomeStatisticFilter filter) throws InvalidException {
        StatisticCountProjections statisticCountProjections = iUserFactory.getStatisticCountOfAdmin();

        List<RevenueStatistic> revenueStatistics = iBookHomeFactory.getRevenueStatisticWithAdminAndYear(filter.getYear());

        return AdminStatistic.builder()
                .totalNumberOfBooking(statisticCountProjections.getNumberOfBooking())
                .totalNumberOfOwner(statisticCountProjections.getNumberOfOwner())
                .totalNumberOfRevenue(statisticCountProjections.getTotalCost())
                .totalNumberOfGuests(statisticCountProjections.getNumberOfGuests())
                .revenueStatistics(revenueStatistics)
                .build();
    }
}
