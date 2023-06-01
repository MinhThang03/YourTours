package com.hcmute.yourtours.factories.websocket;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.models.discount_of_home.projections.NotificationDiscountProjection;

import java.util.List;
import java.util.UUID;

public interface IWebSocketFactory {

    void sendNotificationMessage(UUID userId);

    void sendDiscountNotificationMessage(List<NotificationDiscountProjection> projections) throws InvalidException;
}
