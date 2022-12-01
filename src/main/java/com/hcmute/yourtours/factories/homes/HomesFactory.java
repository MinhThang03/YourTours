package com.hcmute.yourtours.factories.homes;

import com.hcmute.yourtours.commands.HomesCommand;
import com.hcmute.yourtours.enums.CommonStatusEnum;
import com.hcmute.yourtours.enums.ProvinceEnum;
import com.hcmute.yourtours.enums.RefundPolicyEnum;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.factories.amenities_of_home.IAmenitiesOfHomeFactory;
import com.hcmute.yourtours.factories.common.IGetUserFromTokenFactory;
import com.hcmute.yourtours.factories.images_home.IImagesHomeFactory;
import com.hcmute.yourtours.factories.item_favorites.IItemFavoritesFactory;
import com.hcmute.yourtours.factories.owner_of_home.IOwnerOfHomeFactory;
import com.hcmute.yourtours.factories.rooms_of_home.IRoomsOfHomeFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.booking_guest_detail.BookingGuestDetailDetail;
import com.hcmute.yourtours.models.booking_guest_detail.BookingGuestDetailInfo;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.models.homes.HomeInfo;
import com.hcmute.yourtours.models.homes.filter.HomeFilter;
import com.hcmute.yourtours.models.images_home.ImageHomeDetail;
import com.hcmute.yourtours.models.owner_of_home.OwnerOfHomeDetail;
import com.hcmute.yourtours.repositories.HomesRepository;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class HomesFactory
        extends BasePersistDataFactory<UUID, HomeInfo, HomeDetail, Long, HomesCommand>
        implements IHomesFactory {

    protected final HomesRepository homesRepository;
    protected final IImagesHomeFactory iImagesHomeFactory;
    protected final IRoomsOfHomeFactory iRoomsOfHomeFactory;
    protected final IAmenitiesOfHomeFactory iAmenitiesOfHomeFactory;
    protected final IOwnerOfHomeFactory iOwnerOfHomeFactory;
    protected final IGetUserFromTokenFactory iGetUserFromTokenFactory;
    protected final IItemFavoritesFactory iItemFavoritesFactory;

    protected HomesFactory
            (
                    HomesRepository repository,
                    IImagesHomeFactory iImagesHomeFactory,
                    IRoomsOfHomeFactory iRoomsOfHomeFactory,
                    IAmenitiesOfHomeFactory iAmenitiesOfHomeFactory,
                    IOwnerOfHomeFactory iOwnerOfHomeFactory,
                    IGetUserFromTokenFactory iGetUserFromTokenFactory,
                    IItemFavoritesFactory iItemFavoritesFactory
            ) {
        super(repository);
        this.homesRepository = repository;
        this.iImagesHomeFactory = iImagesHomeFactory;
        this.iRoomsOfHomeFactory = iRoomsOfHomeFactory;
        this.iAmenitiesOfHomeFactory = iAmenitiesOfHomeFactory;
        this.iOwnerOfHomeFactory = iOwnerOfHomeFactory;
        this.iGetUserFromTokenFactory = iGetUserFromTokenFactory;
        this.iItemFavoritesFactory = iItemFavoritesFactory;
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
                .status(CommonStatusEnum.ACTIVE)
                .numberOfGuests(detail.getNumberOfGuests())
                .provinceCode(detail.getProvinceCode())
                .addressDetail(detail.getAddressDetail())
                .rank(detail.getRank())
                .view(0L)
                .favorite(0L)
                .thumbnail(detail.getThumbnail())
                .averageRate(detail.getAverageRate())
                .numberOfReviews(0L)
                .refundPolicy(RefundPolicyEnum.BEFORE_ONE_DAY)
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
        entity.setNumberOfGuests(detail.getNumberOfGuests());
        entity.setRank(detail.getRank());
        entity.setProvinceCode(detail.getProvinceCode());
        entity.setAddressDetail(detail.getAddressDetail());
        entity.setView(detail.getView());
        entity.setFavorite(detail.getFavorite());
        entity.setThumbnail(detail.getThumbnail());
        entity.setAverageRate(detail.getAverageRate());
        entity.setNumberOfReviews(detail.getNumberOfReviews());
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
                .numberOfGuests(entity.getNumberOfGuests())
                .imagesOfHome(iImagesHomeFactory.getListByHomeId(entity.getHomeId(), entity.getThumbnail()))
                .provinceCode(entity.getProvinceCode())
                .addressDetail(entity.getAddressDetail())
                .rank(entity.getRank())
                .view(entity.getView())
                .favorite(entity.getFavorite())
                .thumbnail(entity.getThumbnail())
                .averageRate(entity.getAverageRate())
                .numberOfReviews(entity.getNumberOfReviews())
                .lastModifiedDate(entity.getLastModifiedDate())
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
                .numberOfGuests(entity.getNumberOfGuests())
                .provinceCode(entity.getProvinceCode())
                .addressDetail(entity.getAddressDetail())
                .rank(entity.getRank())
                .view(entity.getView())
                .imagesOfHome(iImagesHomeFactory.getFullListByHomeId(entity.getHomeId(), entity.getThumbnail()))
                .favorite(entity.getFavorite())
                .averageRate(entity.getAverageRate())
                .thumbnail(entity.getThumbnail())
                .numberOfReviews(entity.getNumberOfReviews())
                .lastModifiedDate(entity.getLastModifiedDate())
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        return findByHomeId(id).getId();
    }

    @Override
    protected void preCreate(HomeDetail detail) throws InvalidException {
        if (detail.getImagesOfHome().size() < 5) {
            throw new InvalidException(YourToursErrorCode.LIST_IMAGES_HOME_NOT_ENOUGH_SIZE);
        }

        Optional<ImageHomeDetail> image = detail.getImagesOfHome().stream().findFirst();
        image.ifPresent(imageHomeDetail -> detail.setThumbnail(imageHomeDetail.getPath()));
    }

    @Override
    protected void postCreate(HomesCommand entity, HomeDetail detail) throws InvalidException {
        OwnerOfHomeDetail ownerOfHomeDetail = OwnerOfHomeDetail.builder()
                .homeId(entity.getHomeId())
                .isMainOwner(true)
                .userId(UUID.fromString(entity.getCreatedBy()))
                .build();

        iOwnerOfHomeFactory.createModel(ownerOfHomeDetail);

        iImagesHomeFactory.createListWithHomeId(entity.getHomeId(), detail.getImagesOfHome());
        iRoomsOfHomeFactory.createListWithHomeId(entity.getHomeId(), detail.getRoomsOfHome());
        iAmenitiesOfHomeFactory.createListWithHomeId(entity.getHomeId(), detail.getAmenitiesOfHome());
    }


    @Override
    protected <F extends BaseFilter> Page<HomesCommand> pageQuery(F filter, Integer number, Integer size) {
        HomeFilter homeFilter = (HomeFilter) filter;
        return homesRepository.findPageWithFilter
                (
                        homeFilter.getUserId(),
                        homeFilter.getSort() == null ? null : homeFilter.getSort().name(),
                        null,
                        PageRequest.of(number, size)
                );
    }

    @Override
    public HomesCommand findByHomeId(UUID homeId) throws InvalidException {
        HomesCommand home = homesRepository.findByHomeId(homeId).orElse(null);
        if (home == null) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_HOME);
        }
        return home;
    }

    @Override
    public void checkExistsByHomeId(UUID homeId) throws InvalidException {
        HomesCommand home = homesRepository.findByHomeId(homeId).orElse(null);
        if (home == null) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_HOME);
        }
    }

    @Override
    public void checkNumberOfGuestOfHome(UUID homeId, List<BookingGuestDetailDetail> guests) throws InvalidException {
        int numberOfGuest = guests.stream().map(BookingGuestDetailInfo::getNumber).reduce(0, Integer::sum);
        HomeDetail homeDetail = getDetailModel(homeId, null);
        if (numberOfGuest > homeDetail.getNumberOfGuests()) {
            throw new InvalidException(YourToursErrorCode.NUMBER_OF_GUESTS_IS_EXCEED);
        }
    }

    @Override
    public BasePagingResponse<HomeInfo> getFilterWithProvinceName(String search, Integer number, Integer size) throws InvalidException {
        List<Integer> provinceCode = new ArrayList<>();

        for (ProvinceEnum province : ProvinceEnum.values()) {
            if (province.getProvinceName().toUpperCase().contains(search.toUpperCase())) {
                provinceCode.add(province.getProvinceCode());
            }
        }

        if (provinceCode.isEmpty()) {
            return new BasePagingResponse<>(
                    new ArrayList<>(),
                    number,
                    size,
                    0
            );
        }

        List<HomeInfo> result = new ArrayList<>();

        Page<HomesCommand> page = homesRepository.getPageWithListProvinceCode(provinceCode, PageRequest.of(number, size));
        for (HomesCommand homesCommand : page.getContent()) {
            result.add(HomeInfo.builder()
                    .id(homesCommand.getHomeId())
                    .thumbnail(homesCommand.getThumbnail())
                    .name(homesCommand.getName())
                    .costPerNightDefault(homesCommand.getCostPerNightDefault())
                    .build());
        }

        return new BasePagingResponse<>(
                result,
                number,
                size,
                page.getTotalElements()
        );
    }
}
