package com.hcmute.yourtours.models.user_evaluate.projection;

import java.util.UUID;

public interface UserEvaluateProjection {

    UUID getId();

    UUID getHomeId();

    UUID getUserId();

    Double getPoint();

    String getComment();

    String getUserFullName();

    String getUserAvatar();

    String getLastModifiedDate();
}
