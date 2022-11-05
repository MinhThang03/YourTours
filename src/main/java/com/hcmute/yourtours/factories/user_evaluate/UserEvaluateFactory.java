package com.hcmute.yourtours.factories.user_evaluate;

import com.hcmute.yourtours.commands.UserEvaluateCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.user_evaluate.UserEvaluateDetail;
import com.hcmute.yourtours.models.user_evaluate.UserEvaluateInfo;
import com.hcmute.yourtours.repositories.UserEvaluateRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserEvaluateFactory
        extends BasePersistDataFactory<UUID, UserEvaluateInfo, UserEvaluateDetail, Long, UserEvaluateCommand>
        implements IUserEvaluateFactory {

    private final UserEvaluateRepository userEvaluateRepository;

    protected UserEvaluateFactory(UserEvaluateRepository repository) {
        super(repository);
        this.userEvaluateRepository = repository;
    }

    @Override
    @NonNull
    protected Class<UserEvaluateDetail> getDetailClass() {
        return UserEvaluateDetail.class;
    }

    @Override
    public UserEvaluateCommand createConvertToEntity(UserEvaluateDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return UserEvaluateCommand.builder()
                .userId(detail.getUserId())
                .homeId(detail.getHomeId())
                .point(detail.getPoint())
                .comment(detail.getComment())
                .build();
    }

    @Override
    public void updateConvertToEntity(UserEvaluateCommand entity, UserEvaluateDetail detail) throws InvalidException {
        entity.setUserId(detail.getUserId());
        entity.setHomeId(detail.getHomeId());
        entity.setPoint(detail.getPoint());
        entity.setComment(detail.getComment());
    }

    @Override
    public UserEvaluateDetail convertToDetail(UserEvaluateCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }

        return UserEvaluateDetail.builder()
                .id(entity.getUserEvaluateId())
                .homeId(entity.getHomeId())
                .userId(entity.getUserId())
                .point(entity.getPoint())
                .comment(entity.getComment())
                .build();
    }

    @Override
    public UserEvaluateInfo convertToInfo(UserEvaluateCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }

        return UserEvaluateInfo.builder()
                .id(entity.getUserEvaluateId())
                .homeId(entity.getHomeId())
                .userId(entity.getUserId())
                .point(entity.getPoint())
                .comment(entity.getComment())
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        return findByUserRateHomeId(id).getId();
    }

    @Override
    protected UserEvaluateDetail aroundCreate(UserEvaluateDetail detail) throws InvalidException {
        Optional<UserEvaluateCommand> optional = userEvaluateRepository
                .findByUserIdAndHomeId(detail.getUserId(), detail.getHomeId());

        if (optional.isEmpty()) {
            return super.aroundCreate(detail);
        }

        return updateModel(optional.get().getUserEvaluateId(), detail);
    }

    private UserEvaluateCommand findByUserRateHomeId(UUID id) throws InvalidException {
        Optional<UserEvaluateCommand> optional = userEvaluateRepository.findByUserEvaluateId(id);
        if (optional.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_EVALUATE);
        }
        return optional.get();
    }

    @Override
    public Double getAverageRateOfHome(UUID homeId) {
        List<UserEvaluateCommand> evaluates = userEvaluateRepository.findAllByHomeIdAndPointIsNotNull(homeId);

        if (evaluates.isEmpty()) {
            return null;
        }

        Double point = evaluates.stream().map(UserEvaluateCommand::getPoint).reduce(0.0, Double::sum);
        int count = evaluates.size();
        return point / count;
    }
}
