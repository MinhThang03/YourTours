package com.hcmute.yourtours.factories.beds_of_home;

import com.hcmute.yourtours.entities.BedsOfHome;
import com.hcmute.yourtours.factories.bed_categories.IBedCategoriesFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.beds_of_home.BedOfHomeDetail;
import com.hcmute.yourtours.models.beds_of_home.BedOfHomeInfo;
import com.hcmute.yourtours.models.beds_of_home.models.CreateListBedOfHomeDetail;
import com.hcmute.yourtours.models.beds_of_home.responses.CreateListBedOfHomeResponse;
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
        extends BasePersistDataFactory<UUID, BedOfHomeInfo, BedOfHomeDetail, UUID, BedsOfHome>
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
    public BedsOfHome createConvertToEntity(BedOfHomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }

        return BedsOfHome.builder()
                .categoryId(detail.getCategoryId())
                .roomOfHomeId(detail.getRoomOfHomeId())
                .amount(detail.getAmount())
                .build();
    }

    @Override
    public void updateConvertToEntity(BedsOfHome entity, BedOfHomeDetail detail) throws InvalidException {
        entity.setCategoryId(detail.getCategoryId());
        entity.setRoomOfHomeId(detail.getRoomOfHomeId());
        entity.setAmount(detail.getAmount());
    }

    @Override
    public BedOfHomeDetail convertToDetail(BedsOfHome entity) throws InvalidException {
        if (entity == null) {
            return null;
        }

        return BedOfHomeDetail.builder()
                .id(entity.getId())
                .categoryId(entity.getCategoryId())
                .roomOfHomeId(entity.getRoomOfHomeId())
                .amount(entity.getAmount())
                .build();
    }

    @Override
    public BedOfHomeInfo convertToInfo(BedsOfHome entity) throws InvalidException {
        if (entity == null) {
            return null;
        }

        return BedOfHomeInfo.builder()
                .id(entity.getId())
                .categoryId(entity.getCategoryId())
                .roomOfHomeId(entity.getRoomOfHomeId())
                .amount(entity.getAmount())
                .build();
    }

    @Override
    public Integer getNumberOfBedWithHomeId(UUID homeId) {
        return bedsOfHomeRepository.countNumberOfBedByHomeId(homeId);
    }

    @Override
    public Integer countByRoomHomeIdAndCategoryId(UUID roomHomeId, UUID categoryId) {
        Optional<BedsOfHome> optional = bedsOfHomeRepository.findByRoomOfHomeIdAndCategoryId(roomHomeId, categoryId);
        if (optional.isPresent()) {
            return optional.get().getAmount();
        }
        return 0;
    }

    @Override
    public CreateListBedOfHomeResponse createListModel(CreateListBedOfHomeDetail request) throws InvalidException {
        if (request == null || request.getListBedOfHomeDetail() == null) {
            return null;
        }

        UUID roomHomeId = null;

        for (BedOfHomeDetail item : request.getListBedOfHomeDetail()) {
            createModel(item);
            roomHomeId = item.getRoomOfHomeId();
        }

        return CreateListBedOfHomeResponse.builder()
                .success(true)
                .bedDescription(getDescriptionNumberBedOfRoom(roomHomeId))
                .build();
    }

    @Override
    public String getDescriptionNumberBedOfRoom(UUID roomHomeId) throws InvalidException {
        List<BedsOfHome> roomsOfHome = bedsOfHomeRepository.findAllByRoomOfHomeId(roomHomeId);
        if (roomsOfHome.isEmpty()) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        for (BedsOfHome item : roomsOfHome) {
            builder.append(item.getAmount());
            builder.append(" ");
            builder.append(iBedCategoriesFactory.getDetailModel(item.getCategoryId(), null).getName());
            builder.append(" ");
        }
        return builder.toString();
    }

    @Override
    public void deleteAllByRoomHomeId(UUID roomHomeId) throws InvalidException {
        List<BedsOfHome> roomsOfHome = bedsOfHomeRepository.findAllByRoomOfHomeId(roomHomeId);
        if (!roomsOfHome.isEmpty()) {
            for (BedsOfHome item : roomsOfHome) {
                deleteModel(item.getId(), null);
            }
        }
    }


    @Override
    protected void preCreate(BedOfHomeDetail detail) throws InvalidException {
        iBedCategoriesFactory.checkExistsByBedCategoryId(detail.getCategoryId());

        Optional<BedsOfHome> optional = bedsOfHomeRepository.findByRoomOfHomeIdAndCategoryId(detail.getRoomOfHomeId(), detail.getCategoryId());
        if (optional.isPresent()) {
            deleteModel(optional.get().getId(), null);
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
