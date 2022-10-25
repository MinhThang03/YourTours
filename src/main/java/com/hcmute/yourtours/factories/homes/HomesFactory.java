package com.hcmute.yourtours.factories.homes;

import com.hcmute.yourtours.commands.HomesCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.models.homes.HomeInfo;
import com.hcmute.yourtours.repositories.HomesRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class HomesFactory
        extends BasePersistDataFactory<UUID, HomeInfo, HomeDetail, Long, HomesCommand>
        implements IHomesFactory {

    private final HomesRepository homesRepository;

    protected HomesFactory(HomesRepository repository) {
        super(repository);
        this.homesRepository = repository;
    }

    @Override
    @NonNull
    protected Class<HomeDetail> getDetailClass() {
        return HomeDetail.class;
    }

    @Override
    public HomesCommand createConvertToEntity(HomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return HomesCommand.builder()
                .name(detail.getName())
                .description(detail.getDescription())
                .wifi(detail.getWifi())
                .passWifi(detail.getPassWifi())
                .ruleOthers(detail.getRuleOthers())
                .timeCheckInStart(detail.getTimeCheckInStart())
                .timeCheckInEnd(detail.getTimeCheckInEnd())
                .timeCheckOut(detail.getTimeCheckOut())
                .guide(detail.getGuide())
                .costPerNightDefault(detail.getCostPerNightDefault())
                .refundPolicy(detail.getRefundPolicy())
                .status(detail.getStatus())
                .build();
    }

    @Override
    public void updateConvertToEntity(HomesCommand entity, HomeDetail detail) throws InvalidException {
        entity.setName(detail.getName());
        entity.setDescription(detail.getDescription());
        entity.setWifi(detail.getWifi());
        entity.setPassWifi(detail.getPassWifi());
        entity.setRuleOthers(detail.getRuleOthers());
        entity.setTimeCheckInStart(detail.getTimeCheckInStart());
        entity.setTimeCheckInEnd(detail.getTimeCheckInEnd());
        entity.setTimeCheckOut(detail.getTimeCheckOut());
        entity.setGuide(detail.getGuide());
        entity.setCostPerNightDefault(detail.getCostPerNightDefault());
        entity.setRefundPolicy(detail.getRefundPolicy());
        entity.setStatus(detail.getStatus());
    }

    @Override
    public HomeDetail convertToDetail(HomesCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return HomeDetail.builder()
                .id(entity.getHomeId())
                .name(entity.getName())
                .description(entity.getDescription())
                .wifi(entity.getWifi())
                .passWifi(entity.getPassWifi())
                .ruleOthers(entity.getRuleOthers())
                .timeCheckInStart(entity.getTimeCheckInStart())
                .timeCheckInEnd(entity.getTimeCheckInEnd())
                .timeCheckOut(entity.getTimeCheckOut())
                .guide(entity.getGuide())
                .costPerNightDefault(entity.getCostPerNightDefault())
                .refundPolicy(entity.getRefundPolicy())
                .status(entity.getStatus())
                .build();
    }

    @Override
    public HomeInfo convertToInfo(HomesCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return HomeInfo.builder()
                .id(entity.getHomeId())
                .name(entity.getName())
                .description(entity.getDescription())
                .wifi(entity.getWifi())
                .passWifi(entity.getPassWifi())
                .ruleOthers(entity.getRuleOthers())
                .timeCheckInStart(entity.getTimeCheckInStart())
                .timeCheckInEnd(entity.getTimeCheckInEnd())
                .timeCheckOut(entity.getTimeCheckOut())
                .guide(entity.getGuide())
                .costPerNightDefault(entity.getCostPerNightDefault())
                .refundPolicy(entity.getRefundPolicy())
                .status(entity.getStatus())
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        HomesCommand home = homesRepository.findByHomeId(id).orElse(null);
        if (home == null) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_HOME);
        }
        return home.getId();
    }
}
