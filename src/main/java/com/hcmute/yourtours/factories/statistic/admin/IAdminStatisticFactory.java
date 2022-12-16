package com.hcmute.yourtours.factories.statistic.admin;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.models.statistic.admin.filter.AdminHomeStatisticFilter;
import com.hcmute.yourtours.models.statistic.admin.models.AdminStatistic;

public interface IAdminStatisticFactory {
    AdminStatistic statistic(AdminHomeStatisticFilter filter) throws InvalidException;
}
