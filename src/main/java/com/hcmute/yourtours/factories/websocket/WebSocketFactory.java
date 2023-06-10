package com.hcmute.yourtours.factories.websocket;


import com.hcmute.yourtours.entities.Homes;
import com.hcmute.yourtours.entities.User;
import com.hcmute.yourtours.enums.NotificationType;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.factories.notification.INotificationFactory;
import com.hcmute.yourtours.factories.user.IUserFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.models.discount_of_home.projections.NotificationDiscountProjection;
import com.hcmute.yourtours.models.notification.NotificationInfo;
import com.hcmute.yourtours.models.websocket.WebSocketNotification;
import com.hcmute.yourtours.repositories.HomesRepository;
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
    private final HomesRepository homesRepository;
    private final INotificationFactory iNotificationFactory;

    public WebSocketFactory
            (
                    SimpMessagingTemplate simpMessagingTemplate,
                    IUserFactory iUserFactory,
                    HomesRepository homesRepository,
                    INotificationFactory iNotificationFactory
            ) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.iUserFactory = iUserFactory;
        this.homesRepository = homesRepository;
        this.iNotificationFactory = iNotificationFactory;
    }

    @Override
    public void sendNotificationMessage(UUID userId, String title) {
        try {
            User user = iUserFactory.addNumberNotification(userId);

            WebSocketNotification message = WebSocketNotification.builder()
                    .title(title)
                    .number(user.getNumberOfNotification())
                    .build();

            simpMessagingTemplate.convertAndSend(NOTIFICATION_DESTINATION + userId.toString(), message);
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
                    .concat(" vừa tạo chương trình khuyến mãi ")
                    .concat(projection.getDiscountName().toUpperCase());

            for (NotificationDiscountProjection item : projections) {
                NotificationInfo model = NotificationInfo.builder()
                        .homeId(item.getHomeId())
                        .userId(item.getUserId())
                        .title("KHUYẾN MÃI")
                        .thumbnail(item.getThumbnail())
                        .type(NotificationType.HOME_NOTIFICATION)
                        .description(message)
                        .view(false)
                        .build();

                iNotificationFactory.createModel(model);

                sendNotificationMessage(item.getUserId(), "Chương trình khuyến mãi");
            }
        }
    }

    @Override
    public void sendCheckInMessage(UUID userId, UUID homeId) throws InvalidException {

        Homes home = getHomeById(homeId);

        NotificationInfo model = NotificationInfo.builder()
                .homeId(home.getId())
                .userId(userId)
                .title("NHẬN PHÒNG")
                .type(NotificationType.BOOKING_NOTIFICATION)
                .description("Bạn vừa nhận phòng ".concat(home.getName()).concat(" thành công!"))
                .thumbnail(home.getThumbnail())
                .view(false)
                .build();

        iNotificationFactory.createModel(model);

        sendNotificationMessage(userId, "Thông báo nhận phòng");
    }

    @Override
    public void sendCheckOutMessage(UUID userId, UUID homeId) throws InvalidException {

        Homes home = getHomeById(homeId);


        NotificationInfo model = NotificationInfo.builder()
                .homeId(home.getId())
                .userId(userId)
                .title("TRẢ PHÒNG")
                .type(NotificationType.BOOKING_NOTIFICATION)
                .description("Bạn vừa trả phòng ".concat(home.getName()).concat(" thành công!"))
                .thumbnail(home.getThumbnail())
                .view(false)
                .build();

        iNotificationFactory.createModel(model);

        sendNotificationMessage(userId, "Thông báo trả phòng");

    }

    @Override
    public void sendBookingSuccessMessage(UUID userId, UUID homeId) throws InvalidException {
        Homes home = getHomeById(homeId);


        NotificationInfo model = NotificationInfo.builder()
                .homeId(home.getId())
                .userId(userId)
                .title("ĐẶT PHÒNG")
                .type(NotificationType.BOOKING_NOTIFICATION)
                .description("Bạn vừa đặt phòng ".concat(home.getName()).concat(" thành công!"))
                .thumbnail(home.getThumbnail())
                .view(false)
                .build();

        iNotificationFactory.createModel(model);

        sendNotificationMessage(userId, "Thông báo đặt phòng");
    }

    @Override
    public void sendCancelMessage(UUID userId, UUID homeId) throws InvalidException {
        Homes home = getHomeById(homeId);


        NotificationInfo model = NotificationInfo.builder()
                .homeId(home.getId())
                .userId(userId)
                .title("HỦY ĐẶT PHÒNG")
                .type(NotificationType.BOOKING_NOTIFICATION)
                .description("Bạn vừa hủy đặt phòng ".concat(home.getName()).concat(" thành công!"))
                .thumbnail(home.getThumbnail())
                .view(false)
                .build();

        iNotificationFactory.createModel(model);

        sendNotificationMessage(userId, "Thông báo hủy đặt phòng");
    }


    private Homes getHomeById(UUID homeId) throws InvalidException {
        return homesRepository.findById(homeId).orElseThrow(
                () -> new InvalidException(YourToursErrorCode.NOT_FOUND_HOME)
        );
    }
}
