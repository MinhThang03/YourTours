package com.hcmute.yourtours.models.user;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class OperationHistoryModel {
    String userName;
    UUID roleId;
    String roleName;
    String descriptionAction;
    String nameTable;
    Long action;
    String userId;
    LocalDateTime date;
    String fullName;
}
