package com.hcmute.yourtours.factories.home_view;

import com.hcmute.yourtours.entities.HomeView;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.home_view.HomeViewInfo;
import com.hcmute.yourtours.repositories.HomeViewRepository;
import lombok.NonNull;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class HomeViewFactory
        extends BasePersistDataFactory<UUID, HomeViewInfo, HomeViewInfo, UUID, HomeView>
        implements IHomeViewFactory {

    protected final HomeViewRepository homeViewRepository;

    protected HomeViewFactory(PagingAndSortingRepository<HomeView, UUID> repository, HomeViewRepository homeViewRepository) {
        super(repository);
        this.homeViewRepository = homeViewRepository;
    }


    @Override
    @NonNull
    protected Class<HomeViewInfo> getDetailClass() {
        return HomeViewInfo.class;
    }

    @Override
    public HomeView createConvertToEntity(HomeViewInfo detail) throws InvalidException {
        return HomeView.builder()
                .homeId(detail.getHomeId())
                .view(detail.getView())
                .build();
    }

    @Override
    public void updateConvertToEntity(HomeView entity, HomeViewInfo detail) throws InvalidException {
        entity.setHomeId(detail.getHomeId());
        entity.setView(detail.getView());
    }

    @Override
    public HomeViewInfo convertToDetail(HomeView entity) throws InvalidException {
        return convertToInfo(entity);
    }

    @Override
    public HomeViewInfo convertToInfo(HomeView entity) throws InvalidException {
        return HomeViewInfo.builder()
                .id(entity.getId())
                .homeId(entity.getHomeId())
                .view(entity.getView())
                .build();
    }

    @Override
    public HomeViewInfo increaseView(UUID homeId) throws InvalidException {

        LocalDate date = LocalDate.now();

        Optional<HomeView> homeView = homeViewRepository
                .findByDate(homeId, date);

        if (homeView.isEmpty()) {
            HomeViewInfo model = HomeViewInfo.builder()
                    .homeId(homeId)
                    .view(1L)
                    .build();

            return createModel(model);
        }

        HomeView entity = homeView.get();
        entity.setView(entity.getView() + 1);
        return convertToInfo(entity);
    }
}
