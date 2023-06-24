package com.hcmute.yourtours.factories.statistic.admin;

import com.hcmute.yourtours.factories.user.IUserFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.models.statistic.admin.filter.AdminHomeStatisticFilter;
import com.hcmute.yourtours.models.statistic.admin.models.AdminStatistic;
import com.hcmute.yourtours.models.statistic.admin.projections.StatisticCountProjections;
import org.springframework.stereotype.Service;

@Service
public class AdminStatisticFactory implements IAdminStatisticFactory {

    private final IUserFactory iUserFactory;

    public AdminStatisticFactory(IUserFactory iUserFactory) {
        this.iUserFactory = iUserFactory;
    }

    @Override
    public AdminStatistic statistic(AdminHomeStatisticFilter filter) throws InvalidException {
        StatisticCountProjections statisticCountProjections = iUserFactory.getStatisticCountOfAdmin(filter.getYear());

        return AdminStatistic.builder()
                .totalNumberOfBooking(statisticCountProjections.getNumberOfBooking())
                .totalNumberOfOwner(statisticCountProjections.getNumberOfOwner())
                .totalNumberOfRevenue(statisticCountProjections.getTotalCost())
                .totalNumberOfGuests(statisticCountProjections.getNumberOfGuests())
                .build();
    }
}
