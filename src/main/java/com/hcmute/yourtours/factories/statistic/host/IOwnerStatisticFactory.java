package com.hcmute.yourtours.factories.statistic.host;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.models.statistic.admin.filter.AdminStatisticDateFilter;
import com.hcmute.yourtours.models.statistic.admin.models.AdminStatisticHome;
import com.hcmute.yourtours.models.statistic.host.filter.OwnerHomeStatisticFilter;
import com.hcmute.yourtours.models.statistic.host.filter.OwnerHomeStatisticMonthFilter;
import com.hcmute.yourtours.models.statistic.host.models.OwnerHomeStatisticMonth;
import com.hcmute.yourtours.models.statistic.host.models.OwnerStatistic;

public interface IOwnerStatisticFactory {
    OwnerStatistic statistic(OwnerHomeStatisticFilter filter) throws InvalidException;

    BasePagingResponse<OwnerHomeStatisticMonth> ownerHomeStatistic(OwnerHomeStatisticMonthFilter filter, Integer number, Integer size) throws InvalidException;

    BasePagingResponse<AdminStatisticHome> adminStatisticHome(AdminStatisticDateFilter filter, Integer number, Integer size) throws InvalidException;

}
