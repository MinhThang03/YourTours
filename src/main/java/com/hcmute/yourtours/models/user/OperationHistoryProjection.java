package com.hcmute.yourtours.models.user;

import java.time.LocalDateTime;
import java.util.UUID;

public interface OperationHistoryProjection {
    String getUserName();

    UUID getRoleId();

    String getRoleName();

    String getDescriptionAction();

    String getFullname();

    String getNameTable();

    Long getAction();

    String getUserId();

    LocalDateTime getDate();
}
