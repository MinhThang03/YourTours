package com.hcmute.yourtours.factories.rooms_of_home;

import com.hcmute.yourtours.commands.RoomsOfHomeCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.factories.beds_of_home.IBedsOfHomeFactory;
import com.hcmute.yourtours.factories.common.IAuthorizationOwnerHomeFactory;
import com.hcmute.yourtours.factories.room_categories.IRoomCategoriesFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.rooms_of_home.RoomOfHomeDetail;
import com.hcmute.yourtours.models.rooms_of_home.RoomOfHomeInfo;
import com.hcmute.yourtours.models.rooms_of_home.filter.RoomOfHomeFilter;
import com.hcmute.yourtours.models.rooms_of_home.models.CreateListRoomOfHomeModel;
import com.hcmute.yourtours.models.rooms_of_home.models.NumberOfRoomsModel;
import com.hcmute.yourtours.models.rooms_of_home.models.RoomOfHomeCreateModel;
import com.hcmute.yourtours.models.rooms_of_home.models.RoomOfHomeDetailWithBedModel;
import com.hcmute.yourtours.models.rooms_of_home.projections.NumberOfRoomsProjections;
import com.hcmute.yourtours.models.rooms_of_home.projections.RoomOfHomeDetailWithBedProjections;
import com.hcmute.yourtours.repositories.RoomsOfHomeRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoomsOfHomeFactory
        extends BasePersistDataFactory<UUID, RoomOfHomeInfo, RoomOfHomeDetail, Long, RoomsOfHomeCommand>
        implements IRoomsOfHomeFactory {

    private final RoomsOfHomeRepository roomsOfHomeRepository;
    private final IRoomCategoriesFactory iRoomCategoriesFactory;
    private final IAuthorizationOwnerHomeFactory iAuthorizationOwnerHomeFactory;
    private final IBedsOfHomeFactory iBedsOfHomeFactory;

    protected RoomsOfHomeFactory
            (
                    RoomsOfHomeRepository repository,
                    @Qualifier("roomCategoriesFactory") IRoomCategoriesFactory iRoomCategoriesFactory,
                    IAuthorizationOwnerHomeFactory iAuthorizationOwnerHomeFactory,
                    IBedsOfHomeFactory iBedsOfHomeFactory
            ) {
        super(repository);
        this.roomsOfHomeRepository = repository;
        this.iRoomCategoriesFactory = iRoomCategoriesFactory;
        this.iAuthorizationOwnerHomeFactory = iAuthorizationOwnerHomeFactory;
        this.iBedsOfHomeFactory = iBedsOfHomeFactory;
    }

    @Override
    @NonNull
    protected Class<RoomOfHomeDetail> getDetailClass() {
        return RoomOfHomeDetail.class;
    }

    @Override
    public RoomsOfHomeCommand createConvertToEntity(RoomOfHomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return RoomsOfHomeCommand.builder()
                .description(detail.getDescription())
                .homeId(detail.getHomeId())
                .categoryId(detail.getCategoryId())
                .name(detail.getName())
                .orderFlag(detail.getOrderFlag())
                .build();
    }

    @Override
    public void updateConvertToEntity(RoomsOfHomeCommand entity, RoomOfHomeDetail detail) throws InvalidException {
        entity.setDescription(detail.getDescription());
        entity.setHomeId(detail.getHomeId());
        entity.setCategoryId(detail.getCategoryId());
        entity.setOrderFlag(detail.getOrderFlag());
    }

    @Override
    public RoomOfHomeDetail convertToDetail(RoomsOfHomeCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return RoomOfHomeDetail.builder()
                .id(entity.getRoomOfHomeId())
                .description(entity.getDescription())
                .homeId(entity.getHomeId())
                .categoryId(entity.getCategoryId())
                .name(entity.getName())
                .orderFlag(entity.getOrderFlag())
                .categoryDetail(iRoomCategoriesFactory.getDetailModel(entity.getCategoryId(), null))
                .descriptionOfBed(iBedsOfHomeFactory.getDescriptionNumberBedOfRoom(entity.getRoomOfHomeId()))
                .build();
    }

    @Override
    public RoomOfHomeInfo convertToInfo(RoomsOfHomeCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return RoomOfHomeInfo.builder()
                .id(entity.getRoomOfHomeId())
                .description(entity.getDescription())
                .homeId(entity.getHomeId())
                .categoryId(entity.getCategoryId())
                .name(entity.getName())
                .orderFlag(entity.getOrderFlag())
                .categoryDetail(iRoomCategoriesFactory.getDetailModel(entity.getCategoryId(), null))
                .descriptionOfBed(iBedsOfHomeFactory.getDescriptionNumberBedOfRoom(entity.getRoomOfHomeId()))
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        RoomsOfHomeCommand room = roomsOfHomeRepository.findByRoomOfHomeId(id).orElse(null);
        if (room == null) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_ROOMS_OF_HOME);
        }
        return room.getId();
    }

    @Override
    public void createListWithHomeId(UUID homeId, List<RoomOfHomeCreateModel> listCreate) throws InvalidException {
        if (homeId == null || listCreate == null) {
            return;
        }

        for (RoomOfHomeCreateModel item : listCreate) {
            if (item.getNumber() != null) {

                int number = 1;
                Optional<RoomsOfHomeCommand> optional = roomsOfHomeRepository.findByHomeIdAndCategoryIdWithMaxOrder(homeId, item.getCategoryId());
                if (optional.isPresent()) {
                    number += optional.get().getOrderFlag();
                }

                for (int i = 0; i < item.getNumber(); i++) {
                    Integer orderFlag = number + i;
                    String name = iRoomCategoriesFactory.getDetailModel(
                                    item.getCategoryId(), null).getName()
                            .concat(" ")
                            .concat(String.valueOf(orderFlag));

                    RoomOfHomeDetail detail = RoomOfHomeDetail.builder()
                            .name(name)
                            .orderFlag(orderFlag)
                            .homeId(homeId)
                            .categoryId(item.getCategoryId())
                            .build();
                    createModel(detail);
                }
            }
        }
    }


    @Override
    public List<NumberOfRoomsModel> getNumberOfRoomCategoryByHomeId(UUID homeId, Boolean important) {
        List<NumberOfRoomsProjections> projections = roomsOfHomeRepository.getNumberOfRoomCategoryByHomeId(homeId, important);
        return projections.stream().map
                        (
                                item -> NumberOfRoomsModel
                                        .builder()
                                        .roomCategoryId(item.getRoomCategoryId())
                                        .roomCategoryName(item.getRoomCategoryName())
                                        .number(item.getNumber())
                                        .build()
                        )
                .collect(Collectors.toList());
    }

    @Override
    public BasePagingResponse<RoomOfHomeInfo> getPageRoomOfHomeOfHost(RoomOfHomeFilter filter, Integer number, Integer size) throws InvalidException {
        if (filter.getHomeId() == null) {
            return null;
        }

        iAuthorizationOwnerHomeFactory.checkOwnerOfHome(filter.getHomeId());

        return getInfoPage(filter, number, size);
    }

    @Override
    public SuccessResponse createWithListModel(CreateListRoomOfHomeModel request) throws InvalidException {
        createListWithHomeId(request.getHomeId(), request.getListCreate());
        return SuccessResponse.builder()
                .success(true)
                .build();
    }

    @Override
    public Long countNumberRoomOfHome(UUID homeId, UUID categoryId) throws InvalidException {
        return roomsOfHomeRepository.countAllByHomeIdAndCategoryId(homeId, categoryId);
    }

    @Override
    public List<RoomOfHomeDetailWithBedModel> getRoomHaveConfigBed(UUID homeId) {
        List<RoomOfHomeDetailWithBedProjections> projections = roomsOfHomeRepository.getRoomHaveConfigBed(homeId);
        return projections.stream().map(
                item -> RoomOfHomeDetailWithBedModel.builder()
                        .nameOfBed(item.getNameOfBed())
                        .roomName(item.getRoomName())
                        .roomHomeId(item.getRoomHomeId())
                        .numberOfBed(item.getNumberOfBed())
                        .build()
        ).collect(Collectors.toList());
    }

    @Override
    public List<RoomOfHomeInfo> getAllByHomeIdAndCategoryId(UUID homeId, UUID categoryId) throws InvalidException {
        List<RoomsOfHomeCommand> commands = roomsOfHomeRepository.findAllByHomeIdAndCategoryId(homeId, categoryId);
        return convertList(commands);
    }

    @Override
    protected void preCreate(RoomOfHomeDetail detail) throws InvalidException {
        iRoomCategoriesFactory.checkExistByRoomCategoryId(detail.getCategoryId());
    }

    @Override
    protected <F extends BaseFilter> Page<RoomsOfHomeCommand> pageQuery(F filter, Integer number, Integer size) throws InvalidException {
        RoomOfHomeFilter roomOfHomeFilter = (RoomOfHomeFilter) filter;
        return roomsOfHomeRepository.getPageWithFilter(roomOfHomeFilter.getHomeId(), PageRequest.of(number, size));
    }


    @Override
    protected <F extends BaseFilter> void postDelete(UUID id, F filter) throws InvalidException {
        iBedsOfHomeFactory.deleteAllByRoomHomeId(id);
    }
}
