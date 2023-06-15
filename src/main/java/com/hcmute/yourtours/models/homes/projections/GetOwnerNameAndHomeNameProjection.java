package com.hcmute.yourtours.models.homes.projections;

import java.util.UUID;

public interface GetOwnerNameAndHomeNameProjection {
    String getHomeName();

    String getOwnerName();

    Double getBaseCost();

    UUID getOwnerId();
}
