package com.hcmute.yourtours.factories.statistic.host;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.models.statistic.host.filter.OwnerHomeStatisticFilter;
import com.hcmute.yourtours.models.statistic.host.models.OwnerStatistic;

public interface IOwnerStatisticFactory {
    OwnerStatistic statistic(OwnerHomeStatisticFilter filter) throws InvalidException;
}
