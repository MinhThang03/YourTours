package com.hcmute.yourtours.factories.booking.cms;

import com.hcmute.yourtours.commands.BookHomesCommand;
import com.hcmute.yourtours.factories.booking.BookHomeFactory;
import com.hcmute.yourtours.factories.common.IGetUserFromTokenFactory;
import com.hcmute.yourtours.factories.homes.IHomesFactory;
import com.hcmute.yourtours.factories.user.IUserFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.booking.filter.CmsBookingFilter;
import com.hcmute.yourtours.repositories.BookHomeRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CmsBookHomeFactory extends BookHomeFactory {

    private final IGetUserFromTokenFactory iGetUserFromTokenFactory;

    protected CmsBookHomeFactory(
            BookHomeRepository repository,
            @Qualifier("cmsHomesFactory") IHomesFactory iHomesFactory,
            IUserFactory iUserFactory,
            IGetUserFromTokenFactory iGetUserFromTokenFactory) {
        super(repository, iHomesFactory, iUserFactory);
        this.iGetUserFromTokenFactory = iGetUserFromTokenFactory;
    }

    @Override
    protected <F extends BaseFilter> Page<BookHomesCommand> pageQuery(F filter, Integer number, Integer size) throws InvalidException {
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
}
