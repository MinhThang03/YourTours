package com.hcmute.yourtours.factories.statistic.admin;

import com.hcmute.yourtours.factories.booking.IBookHomeFactory;
import com.hcmute.yourtours.factories.user.IUserFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.util.TimeUtil;
import com.hcmute.yourtours.models.statistic.admin.filter.AdminHomeStatisticFilter;
import com.hcmute.yourtours.models.statistic.admin.filter.AdminStatisticDateFilter;
import com.hcmute.yourtours.models.statistic.admin.models.AdminStatistic;
import com.hcmute.yourtours.models.statistic.admin.models.AdminStatisticRevenue;
import com.hcmute.yourtours.models.statistic.admin.projections.AdminRevenueProjection;
import com.hcmute.yourtours.models.statistic.admin.projections.StatisticCountProjections;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminStatisticFactory implements IAdminStatisticFactory {

    private final IUserFactory iUserFactory;
    private final IBookHomeFactory iBookHomeFactory;

    public AdminStatisticFactory(IUserFactory iUserFactory,
                                 @Qualifier("bookHomeFactory") IBookHomeFactory iBookHomeFactory) {
        this.iUserFactory = iUserFactory;
        this.iBookHomeFactory = iBookHomeFactory;
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

    @Override
    public BasePagingResponse<AdminStatisticRevenue> adminStatisticRevenue(AdminStatisticDateFilter filter, Integer number, Integer size) {

        Page<AdminRevenueProjection> pageResult = iBookHomeFactory.getAdminStatisticRevenue(filter, PageRequest.of(number, size));

        List<AdminStatisticRevenue> result = pageResult.getContent().stream().map(
                item -> AdminStatisticRevenue.builder()
                        .homeName(item.getHomeName())
                        .totalCost(item.getTotalCost())
                        .customerName(item.getCustomerName())
                        .ownerName(item.getOwnerName())
                        .adminCost(item.getAdminCost())
                        .createdDate(TimeUtil.toStringDate(item.getCreatedDate()))
                        .build()

        ).collect(Collectors.toList());

        return new BasePagingResponse<>(
                result,
                number,
                size,
                pageResult.getTotalElements());
    }
}
