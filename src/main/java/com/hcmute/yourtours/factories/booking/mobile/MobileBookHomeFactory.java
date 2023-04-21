package com.hcmute.yourtours.factories.booking.mobile;

import com.hcmute.yourtours.entities.BookHomes;
import com.hcmute.yourtours.enums.BookRoomStatusEnum;
import com.hcmute.yourtours.enums.MobileBookHomeStatusEnum;
import com.hcmute.yourtours.external_modules.email.IEmailFactory;
import com.hcmute.yourtours.factories.booking.app.AppBookHomeFactory;
import com.hcmute.yourtours.factories.booking_guest_detail.IBookingGuestDetailFactory;
import com.hcmute.yourtours.factories.booking_surcharge_detail.IBookingSurchargeDetailFactory;
import com.hcmute.yourtours.factories.common.IGetUserFromTokenFactory;
import com.hcmute.yourtours.factories.homes.IHomesFactory;
import com.hcmute.yourtours.factories.owner_of_home.IOwnerOfHomeFactory;
import com.hcmute.yourtours.factories.price_of_home.IPriceOfHomeFactory;
import com.hcmute.yourtours.factories.surcharges_of_home.ISurchargeOfHomeFactory;
import com.hcmute.yourtours.factories.user.IUserFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.booking.BookHomeInfo;
import com.hcmute.yourtours.models.booking.filter.MobileBookingFilter;
import com.hcmute.yourtours.models.booking.projections.MobileGetPageBookingProjection;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.repositories.BookHomeRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class MobileBookHomeFactory extends AppBookHomeFactory implements IMobileBookHomeFactory {

    protected MobileBookHomeFactory
            (
                    BookHomeRepository repository,
                    @Qualifier("appHomesFactory") IHomesFactory iHomesFactory,
                    IUserFactory iUserFactory,
                    ISurchargeOfHomeFactory iSurchargeOfHomeFactory,
                    IPriceOfHomeFactory iPriceOfHomeFactory,
                    IBookingSurchargeDetailFactory iBookingSurchargeDetailFactory,
                    IBookingGuestDetailFactory iBookingGuestDetailFactory,
                    IGetUserFromTokenFactory iGetUserFromTokenFactory,
                    IOwnerOfHomeFactory iOwnerOfHomeFactory,
                    IEmailFactory iEmailFactory,
                    ApplicationEventPublisher applicationEventPublisher
            ) {
        super(repository,
                iHomesFactory,
                iUserFactory,
                iSurchargeOfHomeFactory,
                iPriceOfHomeFactory,
                iBookingSurchargeDetailFactory,
                iBookingGuestDetailFactory,
                iGetUserFromTokenFactory,
                iOwnerOfHomeFactory,
                iEmailFactory,
                applicationEventPublisher);
    }

    @Override
    protected <F extends BaseFilter> BasePagingResponse<BookHomeInfo> aroundGetPage(F filter, Integer number, Integer size) throws InvalidException {
        UUID customerId = iGetUserFromTokenFactory.checkUnAuthorization();

        if (filter instanceof MobileBookingFilter) {
            MobileBookingFilter appBookingFilter = (MobileBookingFilter) filter;

            List<BookRoomStatusEnum> statusList = getListStatusByMobileStatus(appBookingFilter.getStatus());

            Page<MobileGetPageBookingProjection> projections = bookHomeRepository
                    .getMobileBookingPage(statusList, customerId, PageRequest.of(number, size));

            List<BookHomeInfo> content = new ArrayList<>();
            for (MobileGetPageBookingProjection projection : projections.getContent()) {
                content.add(BookHomeInfo.builder()
                        .id(projection.getId())
                        .homeName(projection.getHomeName())
                        .totalCost(projection.getTotalCost())
                        .dateStart(projection.getDateStart())
                        .dateEnd(projection.getDateEnd())
                        .thumbnail(projection.getThumbnail())
                        .homeAddressDetail(projection.getProvinceName())
                        .status(projection.getStatus())
                        .build());

            }

            return new BasePagingResponse<>(
                    content,
                    projections.getNumber(),
                    projections.getSize(),
                    projections.getTotalElements()
            );

        }

        return super.aroundGetPage(filter, number, size);
    }


    private List<BookRoomStatusEnum> getListStatusByMobileStatus(MobileBookHomeStatusEnum status) {
        switch (status) {
            case ACTIVE:
                return List.of(BookRoomStatusEnum.WAITING, BookRoomStatusEnum.CHECK_IN);
            case COMPLETED:
                return List.of(BookRoomStatusEnum.CHECK_OUT);
            case CANCELLED:
                return List.of(BookRoomStatusEnum.CANCELED);
        }
        return List.of(BookRoomStatusEnum.WAITING, BookRoomStatusEnum.CHECK_IN, BookRoomStatusEnum.CHECK_OUT, BookRoomStatusEnum.CANCELED);
    }


    @Override
    public BookHomeInfo convertToInfo(BookHomes entity) throws InvalidException {


        HomeDetail homeDetail = iHomesFactory.getDetailModel(entity.getHomeId(), null);

        return BookHomeInfo.builder()
                .dateStart(entity.getDateStart())
                .dateEnd(entity.getDateEnd())
                .phoneNumber(entity.getPhoneNumber())
                .email(entity.getEmail())
                .cost(entity.getCost())
                .paymentMethod(entity.getPaymentMethod())
                .visaAccount(entity.getVisaAccount())
                .homeId(entity.getHomeId())
                .userId(entity.getUserId())
                .status(entity.getStatus())
                .homeName(homeDetail.getName())
                .thumbnail(homeDetail.getThumbnail())
                .totalCost(entity.getTotalCost())
                .id(entity.getId())
                .homeAddressDetail(homeDetail.getAddressDetail())
                .homeProvinceCode(homeDetail.getProvinceCode())
                .percent(entity.getPercent())
                .costOfHost(entity.getCostOfHost())
                .costOfAdmin(entity.getCostOfAdmin())
                .moneyPayed(entity.getMoneyPayed())
                .refundPolicy(homeDetail.getRefundPolicy())
                .build();
    }

}
