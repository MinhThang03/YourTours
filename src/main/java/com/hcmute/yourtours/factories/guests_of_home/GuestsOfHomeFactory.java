package com.hcmute.yourtours.factories.guests_of_home;

import com.hcmute.yourtours.entities.GuestsOfHome;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.guests_of_home.GuestOfHomeDetail;
import com.hcmute.yourtours.models.guests_of_home.GuestOfHomeInfo;
import com.hcmute.yourtours.repositories.GuestsOfHomeRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class GuestsOfHomeFactory
        extends BasePersistDataFactory<UUID, GuestOfHomeInfo, GuestOfHomeDetail, UUID, GuestsOfHome>
        implements IGuestsOfHomeFactory {


    protected GuestsOfHomeFactory(GuestsOfHomeRepository repository) {
        super(repository);
    }

    @Override
    @NonNull
    protected Class<GuestOfHomeDetail> getDetailClass() {
        return GuestOfHomeDetail.class;
    }

    @Override
    public GuestsOfHome createConvertToEntity(GuestOfHomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return GuestsOfHome.builder()
                .categoryId(detail.getCategoryId())
                .homeId(detail.getHomeId())
                .amount(detail.getAmount())
                .build();
    }

    @Override
    public void updateConvertToEntity(GuestsOfHome entity, GuestOfHomeDetail detail) throws InvalidException {
        entity.setCategoryId(detail.getCategoryId());
        entity.setHomeId(detail.getHomeId());
        entity.setAmount(detail.getAmount());
    }

    @Override
    public GuestOfHomeDetail convertToDetail(GuestsOfHome entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return GuestOfHomeDetail.builder()
                .id(entity.getId())
                .categoryId(entity.getCategoryId())
                .homeId(entity.getHomeId())
                .amount(entity.getAmount())
                .build();
    }

    @Override
    public GuestOfHomeInfo convertToInfo(GuestsOfHome entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return GuestOfHomeInfo.builder()
                .id(entity.getId())
                .categoryId(entity.getCategoryId())
                .homeId(entity.getHomeId())
                .amount(entity.getAmount())
                .build();
    }

}
