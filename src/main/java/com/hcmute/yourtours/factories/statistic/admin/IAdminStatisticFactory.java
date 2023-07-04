package com.hcmute.yourtours.factories.statistic.admin;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.models.statistic.admin.filter.AdminHomeStatisticFilter;
import com.hcmute.yourtours.models.statistic.admin.filter.AdminStatisticDateFilter;
import com.hcmute.yourtours.models.statistic.admin.models.AdminStatistic;
import com.hcmute.yourtours.models.statistic.admin.models.AdminStatisticRevenue;

public interface IAdminStatisticFactory {
    AdminStatistic statistic(AdminHomeStatisticFilter filter) throws InvalidException;

    BasePagingResponse<AdminStatisticRevenue> adminStatisticRevenue(AdminStatisticDateFilter filter, Integer number, Integer size);

}
