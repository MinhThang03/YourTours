package com.hcmute.yourtours.factories.notification;

import com.hcmute.yourtours.entities.Notification;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.factories.common.IGetUserFromTokenFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.libs.util.TimeUtil;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.notification.NotificationInfo;
import com.hcmute.yourtours.repositories.NotificationRepository;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotificationFactory
        extends BasePersistDataFactory<UUID, NotificationInfo, NotificationInfo, UUID, Notification>
        implements INotificationFactory {

    private final NotificationRepository notificationRepository;
    private final IGetUserFromTokenFactory iGetUserFromTokenFactory;

    protected NotificationFactory(NotificationRepository repository,
                                  IGetUserFromTokenFactory iGetUserFromTokenFactory) {
        super(repository);
        this.notificationRepository = repository;
        this.iGetUserFromTokenFactory = iGetUserFromTokenFactory;
    }

    @Override
    @NonNull
    protected Class<NotificationInfo> getDetailClass() {
        return NotificationInfo.class;
    }

    @Override
    public Notification createConvertToEntity(NotificationInfo detail) throws InvalidException {
        return Notification.builder()
                .homeId(detail.getHomeId())
                .thumbnail(detail.getThumbnail())
                .title(detail.getTitle())
                .description(detail.getDescription())
                .view(detail.isView())
                .userId(detail.getUserId())
                .type(detail.getType())
                .build();
    }

    @Override
    public void updateConvertToEntity(Notification entity, NotificationInfo detail) throws InvalidException {
        entity.setDescription(detail.getDescription());
        entity.setUserId(detail.getUserId());
        entity.setTitle(detail.getTitle());
        entity.setHomeId(detail.getHomeId());
        entity.setView(detail.isView());
        entity.setThumbnail(detail.getThumbnail());
        entity.setType(detail.getType());
    }

    @Override
    public NotificationInfo convertToDetail(Notification entity) throws InvalidException {
        return convertToInfo(entity);
    }


    @Override
    public NotificationInfo convertToInfo(Notification entity) throws InvalidException {
        return NotificationInfo.builder()
                .id(entity.getId())
                .homeId(entity.getHomeId())
                .thumbnail(entity.getThumbnail())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .view(entity.isView())
                .type(entity.getType())
                .userId(entity.getUserId())
                .createDate(TimeUtil.toStringDate(entity.getCreatedDate()))
                .build();
    }

    @Override
    public SuccessResponse tickView(UUID notificationId) throws InvalidException {
        Notification notification = repository.findById(notificationId).orElseThrow(
                () -> new InvalidException(YourToursErrorCode.NOT_FOUND_NOTIFICATION)
        );

        notification.setView(true);
        repository.save(notification);

        return SuccessResponse.builder()
                .success(true)
                .build();
    }

    @Override
    public SuccessResponse deleteListNotificationOfCurrentUser(Boolean all) throws InvalidException {

        UUID userId = iGetUserFromTokenFactory.checkUnAuthorization();

        if (Boolean.TRUE.equals(all)) {
            if (notificationRepository.existsByUserId(userId)) {
                notificationRepository.deleteAllByUserId(userId);
            }
        } else {
            if (notificationRepository.existsByUserIdAndView(userId, true)) {
                notificationRepository.deleteAllByUserIdAndView(userId, true);
            }
        }

        return SuccessResponse.builder()
                .success(true)
                .build();
    }

    @Override
    protected <F extends BaseFilter> Page<Notification> pageQuery(F filter, Integer number, Integer size) throws InvalidException {

        UUID userId = iGetUserFromTokenFactory.checkUnAuthorization();
        return notificationRepository.getPageByUserId(userId, PageRequest.of(number, size));
    }
}
