package com.hcmute.yourtours.models.statistic.admin.projections;

import java.time.LocalDateTime;

public interface AdminRevenueProjection {

    String getCustomerName();

    String getOwnerName();

    Double getTotalCost();

    Double getAdminCost();

    String getHomeName();

    LocalDateTime getCreatedDate();


}
