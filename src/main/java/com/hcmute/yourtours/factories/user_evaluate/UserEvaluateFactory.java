package com.hcmute.yourtours.factories.user_evaluate;

import com.hcmute.yourtours.entities.UserEvaluate;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.factories.booking.IBookHomeFactory;
import com.hcmute.yourtours.factories.user.IUserFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.user.UserDetail;
import com.hcmute.yourtours.models.user_evaluate.UserEvaluateDetail;
import com.hcmute.yourtours.models.user_evaluate.UserEvaluateInfo;
import com.hcmute.yourtours.repositories.UserEvaluateRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserEvaluateFactory
        extends BasePersistDataFactory<UUID, UserEvaluateInfo, UserEvaluateDetail, UUID, UserEvaluate>
        implements IUserEvaluateFactory {

    protected final UserEvaluateRepository userEvaluateRepository;
    protected final IUserFactory iUserFactory;
    protected final IBookHomeFactory iBookHomeFactory;

    protected UserEvaluateFactory(
            UserEvaluateRepository repository,
            IUserFactory iUserFactory,
            @Qualifier("bookHomeFactory") IBookHomeFactory iBookHomeFactory
    ) {
        super(repository);
        this.userEvaluateRepository = repository;
        this.iUserFactory = iUserFactory;
        this.iBookHomeFactory = iBookHomeFactory;
    }

    @Override
    @NonNull
    protected Class<UserEvaluateDetail> getDetailClass() {
        return UserEvaluateDetail.class;
    }

    @Override
    public UserEvaluate createConvertToEntity(UserEvaluateDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return UserEvaluate.builder()
                .userId(detail.getUserId())
                .homeId(detail.getHomeId())
                .point(detail.getPoint())
                .comment(detail.getComment())
                .build();
    }

    @Override
    public void updateConvertToEntity(UserEvaluate entity, UserEvaluateDetail detail) throws InvalidException {
        entity.setUserId(detail.getUserId());
        entity.setHomeId(detail.getHomeId());
        entity.setPoint(detail.getPoint());
        entity.setComment(detail.getComment());
    }

    @Override
    public UserEvaluateDetail convertToDetail(UserEvaluate entity) throws InvalidException {
        if (entity == null) {
            return null;
        }

        UserDetail userDetail = iUserFactory.getDetailModel(entity.getUserId(), null);

        return UserEvaluateDetail.builder()
                .id(entity.getId())
                .homeId(entity.getHomeId())
                .userId(entity.getUserId())
                .point(entity.getPoint())
                .comment(entity.getComment())
                .userFullName(userDetail.getFullName())
                .userAvatar(userDetail.getAvatar())
                .build();
    }

    @Override
    public UserEvaluateInfo convertToInfo(UserEvaluate entity) throws InvalidException {
        if (entity == null) {
            return null;
        }

        return UserEvaluateInfo.builder()
                .id(entity.getId())
                .homeId(entity.getHomeId())
                .userId(entity.getUserId())
                .point(entity.getPoint())
                .userFullName(iUserFactory.getDetailModel(entity.getUserId(), null).getFullName())
                .comment(entity.getComment())
                .build();
    }

    @Override
    protected UserEvaluateDetail aroundCreate(UserEvaluateDetail detail) throws InvalidException {
        Optional<UserEvaluate> optional = userEvaluateRepository
                .findByUserIdAndHomeId(detail.getUserId(), detail.getHomeId());

        if (optional.isEmpty()) {
            return super.aroundCreate(detail);
        }

        return updateModel(optional.get().getId(), detail);
    }


    @Override
    public Double getAverageRateOfHome(UUID homeId) {
        List<UserEvaluate> evaluates = userEvaluateRepository.findAllByHomeIdAndPointIsNotNull(homeId);

        if (evaluates.isEmpty()) {
            return null;
        }

        Double point = evaluates.stream().map(UserEvaluate::getPoint).reduce(0.0, Double::sum);
        int count = evaluates.size();
        return point / count;
    }

    @Override
    protected void preCreate(UserEvaluateDetail detail) throws InvalidException {
        iUserFactory.checkExistsByUserId(detail.getUserId());

        if (!iBookHomeFactory.existByUserIdAndHomeId(detail.getUserId(), detail.getHomeId())) {
            throw new InvalidException(YourToursErrorCode.USER_NOT_USE_SERVICE);
        }

    }


}
