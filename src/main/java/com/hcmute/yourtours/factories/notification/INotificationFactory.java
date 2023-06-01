package com.hcmute.yourtours.factories.notification;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.notification.NotificationInfo;

import java.util.UUID;

public interface INotificationFactory extends IDataFactory<UUID, NotificationInfo, NotificationInfo> {

    SuccessResponse tickView(UUID notificationId) throws InvalidException;

    SuccessResponse deleteListNotificationOfCurrentUser(Boolean all) throws InvalidException;
}
