package com.hcmute.yourtours.factories.websocket;


import com.hcmute.yourtours.entities.User;
import com.hcmute.yourtours.enums.NotificationType;
import com.hcmute.yourtours.factories.notification.INotificationFactory;
import com.hcmute.yourtours.factories.user.IUserFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.models.discount_of_home.projections.NotificationDiscountProjection;
import com.hcmute.yourtours.models.notification.NotificationInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class WebSocketFactory implements IWebSocketFactory {

    private static final String NOTIFICATION_DESTINATION = "/topic/notification/";
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final IUserFactory iUserFactory;
    private final INotificationFactory iNotificationFactory;

    public WebSocketFactory(SimpMessagingTemplate simpMessagingTemplate,
                            IUserFactory iUserFactory,
                            INotificationFactory iNotificationFactory) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.iUserFactory = iUserFactory;
        this.iNotificationFactory = iNotificationFactory;
    }

    @Override
    public void sendNotificationMessage(UUID userId) {
        try {
            User user = iUserFactory.addNumberNotification(userId);
            simpMessagingTemplate.convertAndSend(NOTIFICATION_DESTINATION + userId.toString(), user.getNumberOfNotification());
        } catch (Exception e) {
            log.error("ERROR SEND NOTIFICATION SOCKET MESSAGE: {} -- {}", e.getMessage(), e.getCause());
        }
    }

    @Override
    public void sendDiscountNotificationMessage(List<NotificationDiscountProjection> projections) throws InvalidException {
        Optional<NotificationDiscountProjection> optional = projections.stream().findFirst();
        if (optional.isPresent()) {
            NotificationDiscountProjection projection = optional.get();
            String message = projection.getHomeName()
                    .concat("vừa tạo chương trình khuyến mãi ")
                    .concat(projection.getDiscountName().toUpperCase());


            for (NotificationDiscountProjection item : projections) {
                NotificationInfo model = NotificationInfo.builder()
                        .homeId(item.getHomeId())
                        .userId(item.getUserId())
                        .title("KHUYẾN MÃI")
                        .type(NotificationType.HOME_NOTIFICATION)
                        .description(message)
                        .view(false)
                        .build();

                iNotificationFactory.createModel(model);

                sendNotificationMessage(item.getUserId());
            }
        }
    }
}
