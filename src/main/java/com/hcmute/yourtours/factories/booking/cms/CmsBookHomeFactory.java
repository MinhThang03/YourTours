package com.hcmute.yourtours.factories.booking.cms;

import com.hcmute.yourtours.entities.BookHomes;
import com.hcmute.yourtours.external_modules.email.IEmailFactory;
import com.hcmute.yourtours.factories.booking.BookHomeFactory;
import com.hcmute.yourtours.factories.booking_guest_detail.IBookingGuestDetailFactory;
import com.hcmute.yourtours.factories.common.IGetUserFromTokenFactory;
import com.hcmute.yourtours.factories.homes.IHomesFactory;
import com.hcmute.yourtours.factories.owner_of_home.IOwnerOfHomeFactory;
import com.hcmute.yourtours.factories.user.IUserFactory;
import com.hcmute.yourtours.factories.websocket.IWebSocketFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.booking.BookHomeInfo;
import com.hcmute.yourtours.models.booking.filter.CmsBookingFilter;
import com.hcmute.yourtours.models.booking.models.InfoUserBookingModel;
import com.hcmute.yourtours.models.booking.projections.InfoUserBookingProjection;
import com.hcmute.yourtours.models.statistic.admin.filter.AdminStatisticDateFilter;
import com.hcmute.yourtours.repositories.BookHomeRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CmsBookHomeFactory extends BookHomeFactory implements ICmsBookHomeFactory {


    protected CmsBookHomeFactory
            (
                    BookHomeRepository repository,
                    @Qualifier("cmsHomesFactory") IHomesFactory iHomesFactory,
                    IUserFactory iUserFactory,
                    IGetUserFromTokenFactory iGetUserFromTokenFactory,
                    IBookingGuestDetailFactory iBookingGuestDetailFactory,
                    IOwnerOfHomeFactory iOwnerOfHomeFactory,
                    IEmailFactory iEmailFactory,
                    ApplicationEventPublisher applicationEventPublisher,
                    IWebSocketFactory iWebSocketFactory
            ) {
        super(
                repository,
                iHomesFactory,
                iUserFactory,
                iBookingGuestDetailFactory,
                iOwnerOfHomeFactory,
                iGetUserFromTokenFactory,
                iEmailFactory,
                applicationEventPublisher,
                iWebSocketFactory);
    }

    @Override
    protected <F extends BaseFilter> Page<BookHomes> pageQuery(F filter, Integer number, Integer size) throws InvalidException {
        CmsBookingFilter bookingFilter = (CmsBookingFilter) filter;

        UUID ownerId = iGetUserFromTokenFactory.checkUnAuthorization();
        return bookHomeRepository.findAllByCmsFilter
                (
                        bookingFilter.getStatus() == null ? null : bookingFilter.getStatus().name(),
                        ownerId,
                        bookingFilter.getDataStart(),
                        PageRequest.of(number, size)
                );
    }

    @Override
    protected <F extends BaseFilter> BasePagingResponse<BookHomeInfo> aroundGetPage(F filter, Integer number, Integer size) throws InvalidException {
        handleAutoUpdateCheckOut();
        return super.aroundGetPage(filter, number, size);
    }

    @Override
    public BasePagingResponse<InfoUserBookingModel> getStatisticInfoUserBooking(AdminStatisticDateFilter filter, Integer number, Integer size) {
        Page<InfoUserBookingProjection> projections = bookHomeRepository
                .getPageStatisticInfoUserBooking(filter.getDateStart(), filter.getDateEnd(), PageRequest.of(number, size));

        List<InfoUserBookingModel> result = projections.getContent().stream().map
                (
                        item -> InfoUserBookingModel.builder()
                                .numberOfBooking(item.getNumberOfBooking())
                                .userId(item.getUserId())
                                .fullName(item.getFullName())
                                .totalCost(item.getTotalCost())
                                .email(item.getEmail())
                                .rate(item.getAverageRate())
                                .build()
                ).collect(Collectors.toList());

        return new BasePagingResponse<>(
                result,
                number,
                size,
                projections.getTotalElements()
        );
    }
}
