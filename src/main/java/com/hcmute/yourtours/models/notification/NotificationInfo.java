package com.hcmute.yourtours.models.notification;

import com.hcmute.yourtours.enums.NotificationType;
import com.hcmute.yourtours.libs.model.BaseData;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public class NotificationInfo extends BaseData<UUID> {

    private UUID homeId;

    private String thumbnail;

    private String title;

    private String description;

    private boolean view;

    private UUID userId;

    private NotificationType type;
}
