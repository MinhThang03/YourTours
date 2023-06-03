package com.hcmute.yourtours.factories.websocket;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.models.discount_of_home.projections.NotificationDiscountProjection;

import java.util.List;
import java.util.UUID;

public interface IWebSocketFactory {

    void sendNotificationMessage(UUID userId, String title);

    void sendDiscountNotificationMessage(List<NotificationDiscountProjection> projections) throws InvalidException;

    void sendCheckInMessage(UUID userId, UUID homeId) throws InvalidException;

    void sendCheckOutMessage(UUID userId, UUID homeId) throws InvalidException;

    void sendBookingSuccessMessage(UUID userId, UUID homeId) throws InvalidException;

    void sendCancelMessage(UUID userId, UUID homeId) throws InvalidException;

}
