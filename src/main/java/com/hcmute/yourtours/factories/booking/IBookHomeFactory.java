package com.hcmute.yourtours.factories.booking;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.booking.BookHomeDetail;
import com.hcmute.yourtours.models.booking.BookHomeInfo;
import com.hcmute.yourtours.models.booking.models.MonthAndYearModel;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.statistic.admin.filter.AdminHomeChartFilter;
import com.hcmute.yourtours.models.statistic.admin.filter.AdminStatisticDateFilter;
import com.hcmute.yourtours.models.statistic.admin.models.AdminChartStatistic;
import com.hcmute.yourtours.models.statistic.admin.projections.AdminRevenueProjection;
import com.hcmute.yourtours.models.statistic.admin.projections.AdminStatisticHomeProjection;
import com.hcmute.yourtours.models.statistic.common.RevenueStatistic;
import com.hcmute.yourtours.models.statistic.host.models.HomeBookingStatistic;
import com.hcmute.yourtours.models.statistic.host.projections.OwnerHomeStatisticProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IBookHomeFactory extends IDataFactory<UUID, BookHomeInfo, BookHomeDetail> {
    boolean existByUserIdAndHomeId(UUID userId, UUID homeId);

    List<LocalDate> getDatesIsBooked(List<MonthAndYearModel> months, UUID homeId);

    void checkDateBookingOfHomeValid(LocalDate dateStart, LocalDate dateEnd, UUID homeId) throws InvalidException;

    SuccessResponse handleCancelBooking(UUID bookingId) throws InvalidException;

    SuccessResponse handleCheckInBooking(UUID bookingId) throws InvalidException;

    SuccessResponse handleCheckOutBooking(UUID bookingId) throws InvalidException;

    Long totalBookingOfOwner(UUID ownerId, Integer year);

    Long totalBookingOfOwnerFinish(UUID ownerId, Integer year);

    List<HomeBookingStatistic> getHomeBookingStatisticWithOwner(UUID ownerId, Integer year);

    List<RevenueStatistic> getRevenueStatisticWithOwnerAndYear(UUID ownerId, Integer year);

    Page<OwnerHomeStatisticProjection> getStatisticMonthForOwner(UUID userId, Integer month, Integer year, Pageable pageable);

    Page<AdminStatisticHomeProjection> getAdminStatisticHome(AdminStatisticDateFilter filter, Pageable pageable);

    AdminChartStatistic getAdminChart(AdminHomeChartFilter filter);

    Page<AdminRevenueProjection> getAdminStatisticRevenue(AdminStatisticDateFilter filter, Pageable pageable);


}
