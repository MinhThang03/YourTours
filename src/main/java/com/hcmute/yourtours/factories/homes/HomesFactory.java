package com.hcmute.yourtours.factories.homes;

import com.hcmute.yourtours.entities.Homes;
import com.hcmute.yourtours.enums.CommonStatusEnum;
import com.hcmute.yourtours.enums.RefundPolicyEnum;
import com.hcmute.yourtours.enums.UserStatusEnum;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.factories.amenities_of_home.IAmenitiesOfHomeFactory;
import com.hcmute.yourtours.factories.common.IGetUserFromTokenFactory;
import com.hcmute.yourtours.factories.images_home.IImagesHomeFactory;
import com.hcmute.yourtours.factories.item_favorites.IItemFavoritesFactory;
import com.hcmute.yourtours.factories.owner_of_home.IOwnerOfHomeFactory;
import com.hcmute.yourtours.factories.province.IProvinceFactory;
import com.hcmute.yourtours.factories.rooms_of_home.IRoomsOfHomeFactory;
import com.hcmute.yourtours.factories.user.IUserFactory;
import com.hcmute.yourtours.libs.exceptions.ErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.libs.util.TimeUtil;
import com.hcmute.yourtours.models.booking_guest_detail.BookingGuestDetailDetail;
import com.hcmute.yourtours.models.booking_guest_detail.BookingGuestDetailInfo;
import com.hcmute.yourtours.models.homes.CmsHomeInfo;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.models.homes.HomeInfo;
import com.hcmute.yourtours.models.homes.filter.HomeFilter;
import com.hcmute.yourtours.models.homes.projections.CmsHomeInfoProjection;
import com.hcmute.yourtours.models.homes.projections.GetOwnerNameAndHomeNameProjection;
import com.hcmute.yourtours.models.homes.requests.UpdateHomeStatusRequest;
import com.hcmute.yourtours.models.homes.responses.UpdateHomeStatusResponse;
import com.hcmute.yourtours.models.images_home.ImageHomeDetail;
import com.hcmute.yourtours.models.owner_of_home.OwnerOfHomeDetail;
import com.hcmute.yourtours.models.user.UserDetail;
import com.hcmute.yourtours.repositories.HomesRepository;
import com.hcmute.yourtours.utils.GetInfoToken;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class HomesFactory
        extends BasePersistDataFactory<UUID, HomeInfo, HomeDetail, UUID, Homes>
        implements IHomesFactory {

    protected final HomesRepository homesRepository;
    protected final IImagesHomeFactory iImagesHomeFactory;
    protected final IRoomsOfHomeFactory iRoomsOfHomeFactory;
    protected final IAmenitiesOfHomeFactory iAmenitiesOfHomeFactory;
    protected final IOwnerOfHomeFactory iOwnerOfHomeFactory;
    protected final IGetUserFromTokenFactory iGetUserFromTokenFactory;
    protected final IItemFavoritesFactory iItemFavoritesFactory;
    protected final IUserFactory iUserFactory;
    protected final IProvinceFactory iProvinceFactory;

    protected HomesFactory
            (
                    HomesRepository repository,
                    IImagesHomeFactory iImagesHomeFactory,
                    IRoomsOfHomeFactory iRoomsOfHomeFactory,
                    IAmenitiesOfHomeFactory iAmenitiesOfHomeFactory,
                    IOwnerOfHomeFactory iOwnerOfHomeFactory,
                    IGetUserFromTokenFactory iGetUserFromTokenFactory,
                    IItemFavoritesFactory iItemFavoritesFactory,
                    IUserFactory iUserFactory,
                    IProvinceFactory iProvinceFactory
            ) {
        super(repository);
        this.homesRepository = repository;
        this.iImagesHomeFactory = iImagesHomeFactory;
        this.iRoomsOfHomeFactory = iRoomsOfHomeFactory;
        this.iAmenitiesOfHomeFactory = iAmenitiesOfHomeFactory;
        this.iOwnerOfHomeFactory = iOwnerOfHomeFactory;
        this.iGetUserFromTokenFactory = iGetUserFromTokenFactory;
        this.iItemFavoritesFactory = iItemFavoritesFactory;
        this.iUserFactory = iUserFactory;
        this.iProvinceFactory = iProvinceFactory;
    }

    @Override
    @NonNull
    protected Class<HomeDetail> getDetailClass() {
        return HomeDetail.class;
    }

    @Override
    public Homes createConvertToEntity(HomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return Homes.builder()
                .name(detail.getName())
                .description(detail.getDescription())
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
                .numberOfBooking(0L)
                .refundPolicy(RefundPolicyEnum.BEFORE_ONE_DAY)
                .build();
    }

    @Override
    public void updateConvertToEntity(Homes entity, HomeDetail detail) throws InvalidException {
        entity.setName(detail.getName());
        entity.setDescription(detail.getDescription());
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
        entity.setNumberOfBooking(detail.getNumberOfBooking());
    }

    @Override
    public HomeDetail convertToDetail(Homes entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return HomeDetail.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .costPerNightDefault(entity.getCostPerNightDefault())
                .refundPolicy(entity.getRefundPolicy())
                .status(entity.getStatus())
                .numberOfGuests(entity.getNumberOfGuests())
                .imagesOfHome(iImagesHomeFactory.getListByHomeId(entity.getId(), entity.getThumbnail()))
                .provinceCode(entity.getProvinceCode())
                .addressDetail(entity.getAddressDetail())
                .rank(entity.getRank())
                .view(entity.getView())
                .favorite(entity.getFavorite())
                .thumbnail(entity.getThumbnail())
                .provinceName(iProvinceFactory.getProvinceByCodeName(entity.getProvinceCode()).getName())
                .lastModifiedDate(entity.getLastModifiedDate())
                .numberOfBooking(entity.getNumberOfBooking())
                .build();
    }

    @Override
    public HomeInfo convertToInfo(Homes entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return HomeInfo.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .costPerNightDefault(entity.getCostPerNightDefault())
                .refundPolicy(entity.getRefundPolicy())
                .status(entity.getStatus())
                .numberOfGuests(entity.getNumberOfGuests())
                .provinceCode(entity.getProvinceCode())
                .addressDetail(entity.getAddressDetail())
                .rank(entity.getRank())
                .view(entity.getView())
                .imagesOfHome(iImagesHomeFactory.getFullListByHomeId(entity.getId(), entity.getThumbnail()))
                .favorite(entity.getFavorite())
                .thumbnail(entity.getThumbnail())
                .lastModifiedDate(entity.getLastModifiedDate())
                .provinceName(iProvinceFactory.getProvinceByCodeName(entity.getProvinceCode()).getName())
                .numberOfBooking(entity.getNumberOfBooking())
                .build();
    }


    @Override
    protected void preCreate(HomeDetail detail) throws InvalidException {
        if (detail.getImagesOfHome().size() < 5) {
            throw new InvalidException(YourToursErrorCode.LIST_IMAGES_HOME_NOT_ENOUGH_SIZE);
        }

        Optional<ImageHomeDetail> image = detail.getImagesOfHome().stream().findFirst();
        image.ifPresent(imageHomeDetail -> detail.setThumbnail(imageHomeDetail.getPath()));

        UUID userId = iGetUserFromTokenFactory.checkUnAuthorization();
        UserDetail userDetail = iUserFactory.getDetailModel(userId, null);
        if (userDetail.getStatus() == null || !userDetail.getStatus().equals(UserStatusEnum.ACTIVE)) {
            throw new InvalidException(YourToursErrorCode.ACCOUNT_NOT_ACTIVE);
        }
    }

    @Override
    protected void postCreate(Homes entity, HomeDetail detail) throws InvalidException {
        OwnerOfHomeDetail ownerOfHomeDetail = OwnerOfHomeDetail.builder()
                .homeId(entity.getId())
                .isMainOwner(true)
                .userId(UUID.fromString(entity.getCreatedBy()))
                .build();

        iOwnerOfHomeFactory.createModel(ownerOfHomeDetail);

        iImagesHomeFactory.createListWithHomeId(entity.getId(), detail.getImagesOfHome());
        iRoomsOfHomeFactory.createListWithHomeId(entity.getId(), detail.getRoomsOfHome());
        iAmenitiesOfHomeFactory.createListWithHomeId(entity.getId(), detail.getAmenitiesOfHome());
    }


    @Override
    protected <F extends BaseFilter> Page<Homes> pageQuery(F filter, Integer number, Integer size) {
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
    public Homes findByHomeId(UUID homeId) throws InvalidException {
        Homes home = homesRepository.findById(homeId).orElse(null);
        if (home == null) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_HOME);
        }
        return home;
    }

    @Override
    public void checkExistsByHomeId(UUID homeId) throws InvalidException {
        Homes home = homesRepository.findById(homeId).orElse(null);
        if (home == null) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_HOME);
        }
    }

    @Override
    public void checkNumberOfGuestOfHome(UUID homeId, List<BookingGuestDetailDetail> guests) throws InvalidException {
        int numberOfGuest = guests.stream().map(BookingGuestDetailInfo::getNumber).reduce(0, Integer::sum);
        Homes home = homesRepository.findById(homeId).orElse(null);
        if (home == null) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_HOME);
        }
        if (numberOfGuest > home.getNumberOfGuests()) {
            throw new InvalidException(YourToursErrorCode.NUMBER_OF_GUESTS_IS_EXCEED);
        }

        CommonStatusEnum statusOfOwner = iOwnerOfHomeFactory.getStatusOfOwnerHome(homeId);
        if (statusOfOwner.equals(CommonStatusEnum.LOCK)) {
            throw new InvalidException(YourToursErrorCode.OWNER_IS_BLOCK);
        }

    }

    @Override
    public BasePagingResponse<HomeInfo> getFilterWithProvinceName(String search, Integer number, Integer size) throws InvalidException {

        List<HomeInfo> result = new ArrayList<>();

        Page<Homes> page = homesRepository.getPageWithListProvinceCode(search, PageRequest.of(number, size));
        for (Homes homes : page.getContent()) {
            result.add(HomeInfo.builder()
                    .id(homes.getId())
                    .thumbnail(homes.getThumbnail())
                    .name(homes.getName())
                    .costPerNightDefault(homes.getCostPerNightDefault())
                    .build());
        }

        return new BasePagingResponse<>(
                result,
                number,
                size,
                page.getTotalElements()
        );
    }

    @Override
    public BasePagingResponse<CmsHomeInfo> getPageWithRoleAdmin(String search, Integer number, Integer size) throws InvalidException {
        Page<CmsHomeInfoProjection> page = homesRepository.findPageWithFilterWithAdmin(search, PageRequest.of(number, size));

        List<CmsHomeInfo> result = page.getContent().parallelStream().map(
                item -> CmsHomeInfo.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .description(item.getDescription())
                        .addressDetail(item.getAddressDetail())
                        .provinceCode(item.getProvinceCode())
                        .costPerNightDefault(item.getCostPerNightDefault())
                        .refundPolicy(item.getRefundPolicy().getDescription())
                        .status(item.getDeleted() ? "DELETED" : item.getStatus().name())
                        .provinceName(item.getProvinceName())
                        .lastModifiedDate(TimeUtil.toStringDate(item.getLastModifiedDate()))
                        .ownerId(item.getOwnerId())
                        .ownerName(item.getOwnerName())
                        .build()
        ).collect(Collectors.toList());

        return new BasePagingResponse<>(
                result,
                number,
                size,
                page.getTotalElements()
        );
    }

    @Override
    public GetOwnerNameAndHomeNameProjection getOwnerNameAndHomeNameProjection(UUID homeId) {
        return homesRepository.getOwnerNameAndHomeName(homeId);
    }

    @Override
    public UpdateHomeStatusResponse updateHomeStatus(UpdateHomeStatusRequest request) throws InvalidException {

        UUID userId = GetInfoToken.getUserId();
        if (!iOwnerOfHomeFactory.existByOwnerIdAndHomeId(userId, request.getHomeId())) {
            throw new InvalidException(YourToursErrorCode.NOT_OWNER_OF_HOME);
        }

        Homes homes = homesRepository.findById(request.getHomeId()).orElse(null);
        if (homes == null) {
            throw new InvalidException(ErrorCode.NOT_FOUND);
        }

        homes.setStatus(request.getStatus());
        homesRepository.save(homes);

        return UpdateHomeStatusResponse.builder()
                .homeId(homes.getId())
                .status(request.getStatus())
                .build();
    }
}
