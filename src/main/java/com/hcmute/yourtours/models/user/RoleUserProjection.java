package com.hcmute.yourtours.models.user;

import java.util.UUID;

public interface RoleUserProjection {
    UUID getUserId();

    UUID getMenuId();

    String getFactoryName();

    UUID getActionId();

    String getActionCodeName();
}
