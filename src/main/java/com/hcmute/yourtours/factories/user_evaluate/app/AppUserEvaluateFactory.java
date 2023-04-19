package com.hcmute.yourtours.factories.user_evaluate.app;

import com.hcmute.yourtours.entities.UserEvaluate;
import com.hcmute.yourtours.factories.booking.IBookHomeFactory;
import com.hcmute.yourtours.factories.common.IGetUserFromTokenFactory;
import com.hcmute.yourtours.factories.homes.IHomesFactory;
import com.hcmute.yourtours.factories.user.IUserFactory;
import com.hcmute.yourtours.factories.user_evaluate.UserEvaluateFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.models.user_evaluate.UserEvaluateDetail;
import com.hcmute.yourtours.models.user_evaluate.UserEvaluateInfo;
import com.hcmute.yourtours.models.user_evaluate.filter.EvaluateFilter;
import com.hcmute.yourtours.models.user_evaluate.projection.UserEvaluateProjection;
import com.hcmute.yourtours.repositories.UserEvaluateRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AppUserEvaluateFactory extends UserEvaluateFactory {

    private final IGetUserFromTokenFactory iGetUserFromTokenFactory;
    private final IHomesFactory iHomesFactory;

    protected AppUserEvaluateFactory(
            UserEvaluateRepository repository,
            IUserFactory iUserFactory,
            @Qualifier("appBookHomeFactory") IBookHomeFactory iBookHomeFactory,
            IGetUserFromTokenFactory iGetUserFromTokenFactory,
            @Qualifier("homesFactory") IHomesFactory iHomesFactory
    ) {
        super(repository, iUserFactory, iBookHomeFactory);
        this.iGetUserFromTokenFactory = iGetUserFromTokenFactory;
        this.iHomesFactory = iHomesFactory;
    }

    @Override
    protected <F extends BaseFilter> BasePagingResponse<UserEvaluateInfo> aroundGetPage(F filter, Integer number, Integer size) throws InvalidException {
        if (filter instanceof EvaluateFilter) {

            EvaluateFilter evaluateFilter = (EvaluateFilter) filter;

            Optional<String> userId = iGetUserFromTokenFactory.getCurrentUser();
            Page<UserEvaluateProjection> projections;
            if (userId.isEmpty()) {
                projections = userEvaluateRepository
                        .findPageWithoutUserId(evaluateFilter.getHomeId(), PageRequest.of(number, size));
            } else {
                projections = userEvaluateRepository
                        .findPageWithUserId
                                (
                                        UUID.fromString(userId.get()),
                                        evaluateFilter.getHomeId(),
                                        PageRequest.of(number, size)
                                );

            }

            List<UserEvaluateInfo> resultContext = projections
                    .stream()
                    .map(this::projectionToInfo)
                    .collect(Collectors.toList());


            return new BasePagingResponse<>(
                    resultContext,
                    projections.getNumber(),
                    projections.getSize(),
                    projections.getTotalElements()
            );
        }

        return super.aroundGetPage(filter, number, size);
    }


    @Override
    protected void preCreate(UserEvaluateDetail detail) throws InvalidException {
        super.preCreate(detail);
        UUID userId = iGetUserFromTokenFactory.checkUnAuthorization();

        iHomesFactory.checkExistsByHomeId(detail.getHomeId());
        detail.setUserId(userId);
    }

    @Override
    protected void postCreate(UserEvaluate entity, UserEvaluateDetail detail) throws InvalidException {
        handleUpdateAverageRateHome(entity);
    }


    @Override
    protected void postUpdate(UserEvaluate entity, UserEvaluateDetail detail) throws InvalidException {
        handleUpdateAverageRateHome(entity);
    }

    private void handleUpdateAverageRateHome(UserEvaluate entity) throws InvalidException {
        HomeDetail homeDetail = iHomesFactory.getDetailModel(entity.getHomeId(), null);

        if (entity.getPoint() != null) {
            homeDetail.setNumberOfReviews(homeDetail.getNumberOfReviews() + 1);
            homeDetail.setAverageRate(getAverageRateOfHome(homeDetail.getId()));
            iHomesFactory.updateModel(homeDetail.getId(), homeDetail);
        }
    }

    private UserEvaluateInfo projectionToInfo(UserEvaluateProjection projection) {
        return modelBuilder(UserEvaluateInfo.builder(), projection)
                .isMain(isMainReview(projection.getUserId()))
                .build();
    }

    private <T extends UserEvaluateInfo.UserEvaluateInfoBuilder<?, ?>> T modelBuilder(T builder, UserEvaluateProjection projection) {
        builder.id(projection.getId());
        builder.lastModifiedDate(projection.getLastModifiedDate());
        builder.userId(projection.getUserId());
        builder.comment(projection.getComment());
        builder.userAvatar(projection.getUserAvatar());
        builder.point(projection.getPoint());
        builder.userFullName(projection.getUserFullName());
        return builder;
    }

    private boolean isMainReview(UUID userId) {
        Optional<String> currentUserId = iGetUserFromTokenFactory.getCurrentUser();
        return currentUserId.isPresent() && userId.toString().equals(currentUserId.get());
    }
}
