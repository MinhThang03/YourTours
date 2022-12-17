package com.hcmute.yourtours.factories.beds_of_home;

import com.hcmute.yourtours.entities.BedsOfHomeCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.factories.bed_categories.IBedCategoriesFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.beds_of_home.BedOfHomeDetail;
import com.hcmute.yourtours.models.beds_of_home.BedOfHomeInfo;
import com.hcmute.yourtours.models.beds_of_home.models.CreateListBedOfHomeDetail;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.repositories.BedsOfHomeRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class BedsOfHomeFactory
        extends BasePersistDataFactory<UUID, BedOfHomeInfo, BedOfHomeDetail, Long, BedsOfHomeCommand>
        implements IBedsOfHomeFactory {

    protected final IBedCategoriesFactory iBedCategoriesFactory;
    private final BedsOfHomeRepository bedsOfHomeRepository;

    protected BedsOfHomeFactory(BedsOfHomeRepository repository,
                                @Qualifier("bedCategoriesFactory") IBedCategoriesFactory iBedCategoriesFactory) {
        super(repository);
        this.bedsOfHomeRepository = repository;
        this.iBedCategoriesFactory = iBedCategoriesFactory;
    }

    @Override
    @NonNull
    protected Class<BedOfHomeDetail> getDetailClass() {
        return BedOfHomeDetail.class;
    }

    @Override
    public BedsOfHomeCommand createConvertToEntity(BedOfHomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }

        return BedsOfHomeCommand.builder()
                .categoryId(detail.getCategoryId())
                .roomOfHomeId(detail.getRoomOfHomeId())
                .amount(detail.getAmount())
                .build();
    }

    @Override
    public void updateConvertToEntity(BedsOfHomeCommand entity, BedOfHomeDetail detail) throws InvalidException {
        entity.setCategoryId(detail.getCategoryId());
        entity.setRoomOfHomeId(detail.getRoomOfHomeId());
        entity.setAmount(detail.getAmount());
    }

    @Override
    public BedOfHomeDetail convertToDetail(BedsOfHomeCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }

        return BedOfHomeDetail.builder()
                .id(entity.getBedOfHomeId())
                .categoryId(entity.getCategoryId())
                .roomOfHomeId(entity.getRoomOfHomeId())
                .amount(entity.getAmount())
                .build();
    }

    @Override
    public BedOfHomeInfo convertToInfo(BedsOfHomeCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }

        return BedOfHomeInfo.builder()
                .id(entity.getBedOfHomeId())
                .categoryId(entity.getCategoryId())
                .roomOfHomeId(entity.getRoomOfHomeId())
                .amount(entity.getAmount())
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        BedsOfHomeCommand bedConfig = bedsOfHomeRepository.findByBedOfHomeId(id).orElse(null);
        if (bedConfig == null) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_BEDS_OF_HOME);
        }
        return bedConfig.getId();
    }

    @Override
    public Integer getNumberOfBedWithHomeId(UUID homeId) {
        return bedsOfHomeRepository.countNumberOfBedByHomeId(homeId);
    }

    @Override
    public Integer countByRoomHomeIdAndCategoryId(UUID roomHomeId, UUID categoryId) {
        Optional<BedsOfHomeCommand> optional = bedsOfHomeRepository.findByRoomOfHomeIdAndCategoryId(roomHomeId, categoryId);
        if (optional.isPresent()) {
            return optional.get().getAmount();
        }
        return 0;
    }

    @Override
    public SuccessResponse createListModel(CreateListBedOfHomeDetail request) throws InvalidException {
        if (request == null || request.getListBedOfHomeDetail() == null) {
            return null;
        }

        for (BedOfHomeDetail item : request.getListBedOfHomeDetail()) {
            createModel(item);
        }

        return SuccessResponse.builder()
                .success(true)
                .build();
    }

    @Override
    public String getDescriptionNumberBedOfRoom(UUID roomHomeId) throws InvalidException {
        List<BedsOfHomeCommand> roomsOfHome = bedsOfHomeRepository.findAllByRoomOfHomeId(roomHomeId);
        if (roomsOfHome.isEmpty()) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        for (BedsOfHomeCommand item : roomsOfHome) {
            builder.append(item.getAmount());
            builder.append(" ");
            builder.append(iBedCategoriesFactory.getDetailModel(item.getCategoryId(), null).getName());
            builder.append(" ");
        }
        return builder.toString();
    }

    @Override
    public void deleteAllByRoomHomeId(UUID roomHomeId) throws InvalidException {
        List<BedsOfHomeCommand> roomsOfHome = bedsOfHomeRepository.findAllByRoomOfHomeId(roomHomeId);
        if (!roomsOfHome.isEmpty()) {
            for (BedsOfHomeCommand item : roomsOfHome) {
                deleteModel(item.getBedOfHomeId(), null);
            }
        }
    }


    @Override
    protected void preCreate(BedOfHomeDetail detail) throws InvalidException {
        iBedCategoriesFactory.checkExistsByBedCategoryId(detail.getCategoryId());

        Optional<BedsOfHomeCommand> optional = bedsOfHomeRepository.findByRoomOfHomeIdAndCategoryId(detail.getRoomOfHomeId(), detail.getCategoryId());
        if (optional.isPresent()) {
            deleteModel(optional.get().getBedOfHomeId(), null);
        }
    }

    @Override
    protected BedOfHomeDetail aroundCreate(BedOfHomeDetail detail) throws InvalidException {
        if (detail.getAmount() == 0) {
            return null;
        }
        return super.aroundCreate(detail);
    }
}
