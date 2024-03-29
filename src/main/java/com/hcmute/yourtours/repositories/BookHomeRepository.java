package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.BookHomes;
import com.hcmute.yourtours.enums.BookRoomStatusEnum;
import com.hcmute.yourtours.models.booking.projections.GetDetailBookingProjection;
import com.hcmute.yourtours.models.booking.projections.GetPageEvaluateProjection;
import com.hcmute.yourtours.models.booking.projections.InfoUserBookingProjection;
import com.hcmute.yourtours.models.booking.projections.MobileGetPageBookingProjection;
import com.hcmute.yourtours.models.statistic.admin.projections.AdminChartProjection;
import com.hcmute.yourtours.models.statistic.admin.projections.AdminRevenueProjection;
import com.hcmute.yourtours.models.statistic.admin.projections.AdminStatisticHomeProjection;
import com.hcmute.yourtours.models.statistic.host.projections.HomeBookingStatisticProjection;
import com.hcmute.yourtours.models.statistic.host.projections.OwnerHomeStatisticProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookHomeRepository extends JpaRepository<BookHomes, UUID> {


    boolean existsByUserIdAndHomeId(UUID userId, UUID homeId);

    @Query(
            nativeQuery = true,
            value = "select a.*  " +
                    "from book_home a  " +
                    "where :date between DATE(a.date_start) and DATE(a.date_end)  " +
                    "  and a.home_id = :homeId  " +
                    "  and a.status <> 'CANCELED'  " +
                    "limit 1 "
    )
    Optional<BookHomes> findOneByBetweenDate(@Param("date") LocalDate date,
                                             @Param("homeId") UUID homeId);


    @Query(
            nativeQuery = true,
            value = "select a.*  " +
                    "from book_home a,  " +
                    "     owner_of_home b  " +
                    "where a.home_id = b.home_id  " +
                    "  and b.user_id = :userId  " +
                    "  and (a.status = :status or :status is null)  " +
                    "  and (DATE(a.date_start) = DATE(:dateStart) or :dateStart is null)  " +
                    "order by a.date_start asc ",
            countQuery = "select a.id  " +
                    "from book_home a,  " +
                    "     owner_of_home b  " +
                    "where a.home_id = b.home_id  " +
                    "  and b.user_id = :userId  " +
                    "  and (a.status = :status or :status is null)  " +
                    "  and (DATE(a.date_start) = DATE(:dateStart) or :dateStart is null)  " +
                    "order by a.date_start asc "
    )
    Page<BookHomes> findAllByCmsFilter(@Param("status") String status,
                                       @Param("userId") UUID userId,
                                       @Param("dateStart") LocalDate dateStart,
                                       Pageable pageable);


    @Query(
            nativeQuery = true,
            value = "select a.*  " +
                    "from book_home a,  " +
                    "     user b  " +
                    "where b.id = :customerId  " +
                    "  and a.email = b.email  " +
                    "order by a.created_date desc ",
            countQuery = "select a.id  " +
                    "from book_home a,  " +
                    "     user b  " +
                    "where b.id = :customerId  " +
                    "  and a.email = b.email  " +
                    "order by a.created_date desc "
    )
    Page<BookHomes> findBookingOfUser(@Param("customerId") UUID customerId,
                                      Pageable pageable);


    @Query(
            nativeQuery = true,
            value = "select a.* " +
                    "from book_home a " +
                    "where DATE(a.date_end) < DATE(:date) " +
                    "  and a.status = :status "
    )
    List<BookHomes> findAllCommandNeedUpdateCheckOut(@Param("date") LocalDate date,
                                                     @Param("status") String status);


    @Query(
            nativeQuery = true,
            value = "select count(a.id) " +
                    "from book_home a, " +
                    "     owner_of_home b " +
                    "where a.home_id = b.home_id " +
                    "  and YEAR(a.date_start) = :year " +
                    "  and b.user_id = :ownerId "
    )
    Long countTotalBookingOfOwner(UUID ownerId, Integer year);

    @Query(
            nativeQuery = true,
            value = "select count(a.id)  " +
                    "from book_home a,  " +
                    "     owner_of_home b  " +
                    "where a.home_id = b.home_id  " +
                    "  and b.user_id = :ownerId  " +
                    "  and YEAR(a.date_start) = :year " +
                    "  and a.status = :status "
    )
    Long countTotalBookingOfOwnerFinish(UUID ownerId, String status, Integer year);

    @Query(
            nativeQuery = true,
            value = "select a.home_id   as homeId,   " +
                    "       c.name      as homeName,   " +
                    "       count(a.id) as numberOfBooking   " +
                    "from book_home a,   " +
                    "     owner_of_home b,   " +
                    "     homes c   " +
                    "where a.home_id = b.home_id   " +
                    "  and b.user_id = :ownerId   " +
                    "  and a.home_id = c.id   " +
                    "  and YEAR(a.date_start) = :year " +
                    "group by c.name, a.home_id "
    )
    List<HomeBookingStatisticProjection> getHomeBookingStatisticWithOwner(UUID ownerId, Integer year);


    @Query(
            nativeQuery = true,
            value = "select coalesce(sum(a.cost_of_host), 0)   " +
                    "from book_home a,   " +
                    "     owner_of_home b   " +
                    "where a.home_id = b.home_id   " +
                    "  and b.user_id = :ownerId   " +
                    "  and a.status = :status   " +
                    "  and MONTH(a.date_start) = :month   " +
                    "  and YEAR(a.date_start) = :year "
    )
    Double getRevenueWithOwnerIdAndYear(UUID ownerId, String status, Integer month, Integer year);

    @Query(
            nativeQuery = true,
            value = "(select coalesce(sum(a.cost_of_admin), 0) as value,     " +
                    "        1 as month     " +
                    " from book_home a     " +
                    " where a.status = :status     " +
                    "   and MONTH(a.created_date) = 1     " +
                    "   and YEAR(a.created_date) = :year)     " +
                    "union     " +
                    "(select coalesce(sum(a.cost_of_admin), 0) as value,     " +
                    "        2 as month     " +
                    " from book_home a     " +
                    " where a.status = :status     " +
                    "   and MONTH(a.created_date) = 2     " +
                    "   and YEAR(a.created_date) = :year)     " +
                    "union     " +
                    "(select coalesce(sum(a.cost_of_admin), 0) as value,     " +
                    "        3 as month     " +
                    " from book_home a     " +
                    " where a.status = :status     " +
                    "   and MONTH(a.created_date) = 3     " +
                    "   and YEAR(a.created_date) = :year)     " +
                    "union     " +
                    "(select coalesce(sum(a.cost_of_admin), 0) as value,     " +
                    "        4 as month     " +
                    " from book_home a     " +
                    " where a.status = :status     " +
                    "   and MONTH(a.created_date) = 4     " +
                    "   and YEAR(a.created_date) = :year)     " +
                    "union     " +
                    "(select coalesce(sum(a.cost_of_admin), 0) as value,     " +
                    "        5 as month     " +
                    " from book_home a     " +
                    " where a.status = :status     " +
                    "   and MONTH(a.created_date) = 5     " +
                    "   and YEAR(a.created_date) = :year)     " +
                    "union     " +
                    "(select coalesce(sum(a.cost_of_admin), 0) as value,     " +
                    "        6 as month     " +
                    " from book_home a     " +
                    " where a.status = :status     " +
                    "   and MONTH(a.created_date) = 6     " +
                    "   and YEAR(a.created_date) = :year)     " +
                    "union     " +
                    "(select coalesce(sum(a.cost_of_admin), 0) as value,     " +
                    "        7 as month     " +
                    " from book_home a     " +
                    " where a.status = :status     " +
                    "   and MONTH(a.created_date) = 7     " +
                    "   and YEAR(a.created_date) = :year)     " +
                    "union     " +
                    "(select coalesce(sum(a.cost_of_admin), 0) as value,     " +
                    "        8 as month     " +
                    " from book_home a     " +
                    " where a.status = :status     " +
                    "   and MONTH(a.created_date) = 8     " +
                    "   and YEAR(a.created_date) = :year)     " +
                    "union     " +
                    "(select coalesce(sum(a.cost_of_admin), 0) as value,     " +
                    "        9 as month     " +
                    " from book_home a     " +
                    " where a.status = :status     " +
                    "   and MONTH(a.created_date) = 9     " +
                    "   and YEAR(a.created_date) = :year)     " +
                    "union     " +
                    "(select coalesce(sum(a.cost_of_admin), 0) as value,     " +
                    "        10 as month     " +
                    " from book_home a     " +
                    " where a.status = :status     " +
                    "   and MONTH(a.created_date) = 10     " +
                    "   and YEAR(a.created_date) = :year)     " +
                    "union     " +
                    "(select coalesce(sum(a.cost_of_admin), 0) as value,     " +
                    "        11 as month     " +
                    " from book_home a     " +
                    " where a.status = :status     " +
                    "   and MONTH(a.created_date) = 11     " +
                    "   and YEAR(a.created_date) = :year)     " +
                    "union     " +
                    "(select coalesce(sum(a.cost_of_admin), 0) as value,     " +
                    "        12 as month     " +
                    " from book_home a     " +
                    " where a.status = :status     " +
                    "   and MONTH(a.created_date) = 12     " +
                    "   and YEAR(a.created_date) = :year)      "
    )
    List<AdminChartProjection> getRevenueRevenueWithAdminIdAndYear(String status, Integer year);


    @Query(
            nativeQuery = true,
            value = "(select count(a.id) * 1.0 as value,  " +
                    "        1           as month  " +
                    " from book_home a  " +
                    " where MONTH(a.date_start) = 1  " +
                    "   and YEAR(a.date_start) = :year)  " +
                    "union  " +
                    "(select count(a.id) * 1.0 as value,  " +
                    "        2           as month  " +
                    " from book_home a  " +
                    " where MONTH(a.date_start) = 2  " +
                    "   and YEAR(a.date_start) = :year)  " +
                    "union  " +
                    "(select count(a.id) * 1.0 as value,  " +
                    "        3           as month  " +
                    " from book_home a  " +
                    " where MONTH(a.date_start) = 3  " +
                    "   and YEAR(a.date_start) = :year)  " +
                    "union  " +
                    "(select count(a.id) * 1.0 as value,  " +
                    "        4           as month  " +
                    " from book_home a  " +
                    " where MONTH(a.date_start) = 4  " +
                    "   and YEAR(a.date_start) = :year)  " +
                    "union  " +
                    "(select count(a.id) * 1.0 as value,  " +
                    "        5           as month  " +
                    " from book_home a  " +
                    " where MONTH(a.date_start) = 5  " +
                    "   and YEAR(a.date_start) = :year)  " +
                    "union  " +
                    "(select count(a.id) * 1.0 as value,  " +
                    "        6           as month  " +
                    " from book_home a  " +
                    " where MONTH(a.date_start) = 6  " +
                    "   and YEAR(a.date_start) = :year)  " +
                    "union  " +
                    "(select count(a.id) * 1.0 as value,  " +
                    "        7           as month  " +
                    " from book_home a  " +
                    " where MONTH(a.date_start) = 7  " +
                    "   and YEAR(a.date_start) = :year)  " +
                    "union  " +
                    "(select count(a.id) * 1.0 as value,  " +
                    "        8           as month  " +
                    " from book_home a  " +
                    " where MONTH(a.date_start) = 8  " +
                    "   and YEAR(a.date_start) = :year)  " +
                    "union  " +
                    "(select count(a.id) * 1.0 as value,  " +
                    "        9           as month  " +
                    " from book_home a  " +
                    " where MONTH(a.date_start) = 9  " +
                    "   and YEAR(a.date_start) = :year)  " +
                    "union  " +
                    "(select count(a.id) * 1.0 as value,  " +
                    "        10           as month  " +
                    " from book_home a  " +
                    " where MONTH(a.date_start) = 10  " +
                    "   and YEAR(a.date_start) = :year)  " +
                    "union  " +
                    "(select count(a.id) * 1.0 as value,  " +
                    "        11           as month  " +
                    " from book_home a  " +
                    " where MONTH(a.date_start) = 11  " +
                    "   and YEAR(a.date_start) = :year)  " +
                    "union  " +
                    "(select count(a.id) * 1.0 as value,  " +
                    "        12           as month  " +
                    " from book_home a  " +
                    " where MONTH(a.date_start) = 12  " +
                    "   and YEAR(a.date_start) = :year); "
    )
    List<AdminChartProjection> getRevenueBookingWithAdminIdAndYear(Integer year);


    @Query(
            nativeQuery = true,
            value = "select a.numberOfBooking as numberOfBooking,   " +
                    "       a.totalCost       as totalCost,   " +
                    "       a.userId          as userId,   " +
                    "       a.fullName        as fullName,   " +
                    "       a.email           as email,   " +
                    "       b.averageRate     as averageRate   " +
                    "from (select count(a.id)                    as numberOfBooking,   " +
                    "             coalesce(sum(a.total_cost), 0) as totalCost,   " +
                    "             a.user_id                      as userId,   " +
                    "             b.full_name                    as fullName,   " +
                    "             a.email                        as email   " +
                    "      from book_home a   " +
                    "               , user b   " +
                    "      where a.user_id = b.id   " +
                    "        and DATE(a.created_date) between :dateStart and :dateEnd   " +
                    "      group by a.user_id) a   " +
                    "         left join (select a.user_id                  as userId,   " +
                    "                           sum(a.rates) / count(a.id) as averageRate   " +
                    "                    from book_home a   " +
                    "                    where DATE(a.created_date) between :dateStart   " +
                    "                        and :dateEnd   " +
                    "                      and a.rates is not null   " +
                    "                    group by a.user_id) b on a.userId = b.userId ",
            countQuery = "select a.numberOfBooking as numberOfBooking,   " +
                    "       a.totalCost       as totalCost,   " +
                    "       a.userId          as userId,   " +
                    "       a.fullName        as fullName,   " +
                    "       a.email           as email,   " +
                    "       b.averageRate     as averageRate   " +
                    "from (select count(a.id)                    as numberOfBooking,   " +
                    "             coalesce(sum(a.total_cost), 0) as totalCost,   " +
                    "             a.user_id                      as userId,   " +
                    "             b.full_name                    as fullName,   " +
                    "             a.email                        as email   " +
                    "      from book_home a   " +
                    "             , user b   " +
                    "      where a.user_id = b.id   " +
                    "        and DATE(a.created_date) between :dateStart and :dateEnd   " +
                    "      group by a.user_id) a   " +
                    "         left join (select a.user_id                  as userId,   " +
                    "                           sum(a.rates) / count(a.id) as averageRate   " +
                    "                    from book_home a   " +
                    "                    where DATE(a.created_date) between :dateStart   " +
                    "                        and :dateEnd   " +
                    "                      and a.rates is not null   " +
                    "                    group by a.user_id) b on a.userId = b.userId "
    )
    Page<InfoUserBookingProjection> getPageStatisticInfoUserBooking(LocalDate dateStart, LocalDate dateEnd, Pageable pageable);


    @Query(
            value = "select a from BookHomes a " +
                    "where a.userId = :userId " +
                    "and (:status is null or a.status = :status ) order by a.createdDate desc ",
            countQuery = "select a.id from BookHomes a " +
                    "where a.userId = :userId " +
                    "and (:status is null or a.status = :status )"
    )
    Page<BookHomes> getAppBookingPage(@Param("status") BookRoomStatusEnum status,
                                      @Param("userId") UUID userId,
                                      Pageable pageable);


    @Query(
            value = "select a.id as id," +
                    "       a.totalCost as totalCost, " +
                    "       a.dateStart as dateStart, " +
                    "       a.dateEnd as dateEnd, " +
                    "       b.name as homeName," +
                    "       b.thumbnail as thumbnail," +
                    "       c.name as provinceName, " +
                    "       a.userId as userId, " +
                    "       a.homeId as homeId, " +
                    "       a.status as status " +
                    "from BookHomes a inner join Homes b on a.homeId = b.id  " +
                    "inner join Province c on b.provinceCode = c.codeName  " +
                    "where a.userId = :userId " +
                    "and  a.status in :status " +
                    "order by a.createdDate desc ",
            countQuery = "select a.id " +
                    "from BookHomes a inner join Homes b on a.homeId = b.id  " +
                    "inner join Province c on b.provinceCode = c.codeName  " +
                    "where a.userId = :userId " +
                    "and a.status in :status  " +
                    "order by a.createdDate desc "

    )
    Page<MobileGetPageBookingProjection> getMobileBookingPage(@Param("status") List<BookRoomStatusEnum> status,
                                                              @Param("userId") UUID userId,
                                                              Pageable pageable);

    @Query(
            nativeQuery = true,
            value = "select a.id        as bookingId,    " +
                    "       a.user_id   as userId,    " +
                    "       a.home_id   as homeId,    " +
                    "       a.rates     as rates,    " +
                    "       a.comment   as comment,    " +
                    "       b.avatar    as avatar,    " +
                    "       a.last_modified_date    as lastModifiedDate,    " +
                    "       b.full_name as fullName    " +
                    "from book_home a    " +
                    "         inner join user b on a.user_id = b.id and a.comment is not null and a.rates is not null and a.home_id = :homeId    " +
                    "order by a.last_modified_date ",
            countQuery = "select a.id    " +
                    "from book_home a    " +
                    "         inner join user b on a.user_id = b.id and a.comment is not null and a.home_id = :homeId    "
    )
    Page<GetPageEvaluateProjection> getPageEvaluate(@Param("homeId") UUID homeId,
                                                    Pageable pageable);


    @Query(
            nativeQuery = true,
            value = "select b.name                   as homeName,   " +
                    "       b.cost_per_night_default as costPerNight,   " +
                    "       a.id                     as bookingId,   " +
                    "       d.name                   as province,   " +
                    "       a.total_cost             as totalCost,   " +
                    "       a.money_payed            as moneyPayed,   " +
                    "       a.date_start             as dateStart,   " +
                    "       a.date_end               as dateEnd,   " +
                    "       a.created_date           as createdDate,   " +
                    "       a.comment                as comment,   " +
                    "       a.rates                  as rates,   " +
                    "       a.status                 as status,   " +
                    "       a.user_id                as userId,   " +
                    "       a.home_id                as homeId,   " +
                    "       b.refund_policy          as refundPolicy,   " +
                    "       c.full_name              as userName,   " +
                    "       f.full_name              as ownerName   " +
                    "from (select a.* from book_home a where a.id = :id) a   " +
                    "         inner join homes b on a.home_id = b.id   " +
                    "         inner join user c on a.user_id = c.id   " +
                    "         inner join province d on b.province_code = d.code_name   " +
                    "         inner join owner_of_home e on b.id = e.home_id and e.is_main_owner is true   " +
                    "         inner join user f on f.id = e.user_id "
    )
    GetDetailBookingProjection getDetailBooking(@Param("id") UUID id);


    @Query(
            nativeQuery = true,
            value = "select a.homeId                        as homeId,       " +
                    "       a.name                          as homeName,       " +
                    "       coalesce(b.numberOfView, 0)     as numberOfView,       " +
                    "       coalesce(c.numberOfBooking, 0)  as numberOfBooking,       " +
                    "       coalesce(d.numberOfEvaluate, 0) as numberOfEvaluate,       " +
                    "       coalesce(d.point, 0)            as point,       " +
                    "       coalesce(e.revenue, 0)          as revenue       " +
                    "from (select a.id   as homeId,       " +
                    "             a.name as name       " +
                    "      from homes a,       " +
                    "           owner_of_home b       " +
                    "      where a.id = b.home_id       " +
                    "        and b.is_main_owner is true       " +
                    "        and b.user_id = :userId) a       " +
                    "         left join       " +
                    "       " +
                    "     (select a.home_id   as homeId,       " +
                    "             sum(a.view) as numberOfView       " +
                    "      from home_view a       " +
                    "      where MONTH(a.created_date) = :month       " +
                    "        and YEAR(a.created_date) = :year       " +
                    "      group by a.home_id) b on a.homeId = b.homeId       " +
                    "         left join (select a.home_id        as homeId,       " +
                    "                           count(a.home_id) as numberOfBooking       " +
                    "                    from book_home a       " +
                    "                    where MONTH(a.created_date) = :month       " +
                    "                      and YEAR(a.created_date) = :year       " +
                    "                    group by a.home_id) c on a.homeId = c.homeId       " +
                    "         left join (select a.home_id        as homeId,       " +
                    "                           count(a.home_id) as numberOfEvaluate,       " +
                    "                           sum(a.rates)     as point       " +
                    "                    from book_home a       " +
                    "                    where MONTH(a.created_date) = :month       " +
                    "                      and YEAR(a.created_date) = :year       " +
                    "                      and a.rates is not NULL       " +
                    "                    group by a.home_id) d on a.homeId = d.homeId       " +
                    "         left join (select a.home_id           as homeId,       " +
                    "                           sum(a.cost_of_host) as revenue       " +
                    "                    from book_home a       " +
                    "                    where MONTH(a.last_modified_date) = :month       " +
                    "                      and YEAR(a.last_modified_date) = :year       " +
                    "                      and a.status = 'CHECK_OUT'       " +
                    "                    group by a.home_id) e on a.homeId = e.homeId ",
            countQuery = "select a.homeId                        as homeId,       " +
                    "       a.name                          as homeName,       " +
                    "       coalesce(b.numberOfView, 0)     as numberOfView,       " +
                    "       coalesce(c.numberOfBooking, 0)  as numberOfBooking,       " +
                    "       coalesce(d.numberOfEvaluate, 0) as numberOfEvaluate,       " +
                    "       coalesce(d.point, 0)            as point,       " +
                    "       coalesce(e.revenue, 0)          as revenue       " +
                    "from (select a.id   as homeId,       " +
                    "             a.name as name       " +
                    "      from homes a,       " +
                    "           owner_of_home b       " +
                    "      where a.id = b.home_id       " +
                    "        and b.is_main_owner is true       " +
                    "        and b.user_id = :userId) a       " +
                    "         left join       " +
                    "       " +
                    "     (select a.home_id   as homeId,       " +
                    "             sum(a.view) as numberOfView       " +
                    "      from home_view a       " +
                    "      where MONTH(a.created_date) = :month       " +
                    "        and YEAR(a.created_date) = :year       " +
                    "      group by a.home_id) b on a.homeId = b.homeId       " +
                    "         left join (select a.home_id        as homeId,       " +
                    "                           count(a.home_id) as numberOfBooking       " +
                    "                    from book_home a       " +
                    "                    where MONTH(a.created_date) = :month       " +
                    "                      and YEAR(a.created_date) = :year       " +
                    "                    group by a.home_id) c on a.homeId = c.homeId       " +
                    "         left join (select a.home_id        as homeId,       " +
                    "                           count(a.home_id) as numberOfEvaluate,       " +
                    "                           sum(a.rates)     as point       " +
                    "                    from book_home a       " +
                    "                    where MONTH(a.created_date) = :month       " +
                    "                      and YEAR(a.created_date) = :year       " +
                    "                      and a.rates is not NULL       " +
                    "                    group by a.home_id) d on a.homeId = d.homeId       " +
                    "         left join (select a.home_id           as homeId,       " +
                    "                           sum(a.cost_of_host) as revenue       " +
                    "                    from book_home a       " +
                    "                    where MONTH(a.last_modified_date) = :month       " +
                    "                      and YEAR(a.last_modified_date) = :year       " +
                    "                      and a.status = 'CHECK_OUT'       " +
                    "                    group by a.home_id) e on a.homeId = e.homeId "
    )
    Page<OwnerHomeStatisticProjection> ownerHomeStatistic(UUID userId, Integer month, Integer year, Pageable pageable);


    @Query(
            nativeQuery = true,
            value = "select a.homeId                        as homeId,     " +
                    "       a.name                          as homeName,     " +
                    "       a.ownerName                     as ownerName,     " +
                    "       coalesce(b.numberOfView, 0)     as numberOfView,     " +
                    "       coalesce(c.numberOfBooking, 0)  as numberOfBooking,     " +
                    "       coalesce(d.numberOfEvaluate, 0) as numberOfEvaluate,     " +
                    "       coalesce(d.point, 0)            as point,     " +
                    "       coalesce(e.revenue, 0)          as revenue     " +
                    "from (select a.id        as homeId,     " +
                    "             a.name      as name,     " +
                    "             c.full_name as ownerName     " +
                    "      from homes a,     " +
                    "           owner_of_home b,     " +
                    "           user c     " +
                    "      where a.id = b.home_id     " +
                    "        and b.is_main_owner is true     " +
                    "        and b.user_id = c.id) a     " +
                    "         left join     " +
                    "     " +
                    "     (select a.home_id   as homeId,     " +
                    "             sum(a.view) as numberOfView     " +
                    "      from home_view a     " +
                    "      where DATE(a.created_date) between :dateStart and :dateEnd     " +
                    "      group by a.home_id) b on a.homeId = b.homeId     " +
                    "         left join (select a.home_id        as homeId,     " +
                    "                           count(a.home_id) as numberOfBooking     " +
                    "                    from book_home a     " +
                    "                    where DATE(a.created_date) between :dateStart and :dateEnd     " +
                    "                    group by a.home_id) c on a.homeId = c.homeId     " +
                    "         left join (select a.home_id        as homeId,     " +
                    "                           count(a.home_id) as numberOfEvaluate,     " +
                    "                           sum(a.rates)     as point     " +
                    "                    from book_home a     " +
                    "                    where DATE(a.created_date) between :dateStart and :dateEnd     " +
                    "                      and a.rates is not NULL     " +
                    "                    group by a.home_id) d on a.homeId = d.homeId     " +
                    "         left join (select a.home_id           as homeId,     " +
                    "                           sum(a.cost_of_host) as revenue     " +
                    "                    from book_home a     " +
                    "                    where DATE(a.created_date) between :dateStart and :dateEnd     " +
                    "                      and a.status = 'CHECK_OUT'     " +
                    "                    group by a.home_id) e on a.homeId = e.homeId ",
            countQuery = "select a.homeId                    " +
                    "from (select a.id        as homeId,     " +
                    "             a.name      as name,     " +
                    "             c.full_name as ownerName     " +
                    "      from homes a,     " +
                    "           owner_of_home b,     " +
                    "           user c     " +
                    "      where a.id = b.home_id     " +
                    "        and b.is_main_owner is true     " +
                    "        and b.user_id = c.id) a     " +
                    "         left join     " +
                    "     " +
                    "     (select a.home_id   as homeId,     " +
                    "             sum(a.view) as numberOfView     " +
                    "      from home_view a     " +
                    "      where DATE(a.created_date) between :dateStart and :dateEnd     " +
                    "      group by a.home_id) b on a.homeId = b.homeId     " +
                    "         left join (select a.home_id        as homeId,     " +
                    "                           count(a.home_id) as numberOfBooking     " +
                    "                    from book_home a     " +
                    "                    where DATE(a.created_date) between :dateStart and :dateEnd     " +
                    "                    group by a.home_id) c on a.homeId = c.homeId     " +
                    "         left join (select a.home_id        as homeId,     " +
                    "                           count(a.home_id) as numberOfEvaluate,     " +
                    "                           sum(a.rates)     as point     " +
                    "                    from book_home a     " +
                    "                    where DATE(a.created_date) between :dateStart and :dateEnd     " +
                    "                      and a.rates is not NULL     " +
                    "                    group by a.home_id) d on a.homeId = d.homeId     " +
                    "         left join (select a.home_id           as homeId,     " +
                    "                           sum(a.cost_of_host) as revenue     " +
                    "                    from book_home a     " +
                    "                    where DATE(a.created_date) between :dateStart and :dateEnd     " +
                    "                      and a.status = 'CHECK_OUT'     " +
                    "                    group by a.home_id) e on a.homeId = e.homeId "
    )
    Page<AdminStatisticHomeProjection> adminStatisticHome(LocalDate dateStart, LocalDate dateEnd, Pageable pageable);


    @Query(
            nativeQuery = true,
            value = "select c.full_name     as ownerName, " +
                    "       d.full_name     as customerName, " +
                    "       a.created_date  as createdDate, " +
                    "       a.total_cost    as totalCost, " +
                    "       a.cost_of_admin as adminCost, " +
                    "       e.name          as homeName " +
                    "from book_home a, " +
                    "     owner_of_home b, " +
                    "     user c, " +
                    "     user d, " +
                    "     homes e " +
                    "where a.user_id = c.id " +
                    "  and a.home_id = b.home_id " +
                    "  and b.is_main_owner " +
                    "  and b.user_id = d.id " +
                    "  and a.home_id = e.id " +
                    "  and DATE(a.created_date) between :dateStart and :dateEnd  ",
            countQuery = "select a.id " +
                    "from book_home a, " +
                    "     owner_of_home b, " +
                    "     user c, " +
                    "     user d, " +
                    "     homes e " +
                    "where a.user_id = c.id " +
                    "  and a.home_id = b.home_id " +
                    "  and b.is_main_owner " +
                    "  and b.user_id = d.id " +
                    "  and a.home_id = e.id " +
                    "  and DATE(a.created_date) between :dateStart and :dateEnd "
    )
    Page<AdminRevenueProjection> getAdminStatisticRevenue(LocalDate dateStart, LocalDate dateEnd, Pageable pageable);
}
