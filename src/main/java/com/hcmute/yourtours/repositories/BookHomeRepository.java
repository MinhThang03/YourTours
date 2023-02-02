package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.BookHomes;
import com.hcmute.yourtours.models.booking.projections.InfoUserBookingProjection;
import com.hcmute.yourtours.models.statistic.host.projections.HomeBookingStatisticProjection;
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
                    "order by a.date_start desc ",
            countQuery = "select a.*  " +
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
                    "where b.userid = :customerId  " +
                    "  and a.email = b.email  " +
                    "order by a.created_date desc ",
            countQuery = "select a.id  " +
                    "from book_home a,  " +
                    "     user b  " +
                    "where b.userid = :customerId  " +
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
                    "  and a.home_id = c.home_id   " +
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
            value = "select coalesce(sum(a.cost_of_admin), 0)   " +
                    "from book_home a,   " +
                    "     owner_of_home b   " +
                    "where a.home_id = b.home_id   " +
                    "  and a.status = :status   " +
                    "  and MONTH(a.date_start) = :month   " +
                    "  and YEAR(a.date_start) = :year "
    )
    Double getRevenueWithAdminIdAndYear(String status, Integer month, Integer year);


    @Query(
            nativeQuery = true,
            value = "select count(a.id)                      as numberOfBooking, " +
                    "       coalesce(sum(a.total_cost), 0) as totalCost, " +
                    "       a.user_id                        as userId, " +
                    "       b.full_name                      as fullName " +
                    "from book_home a " +
                    "         inner join user b " +
                    "where a.user_id = b.userid " +
                    "group by a.user_id, b.full_name ",
            countQuery = "select count(a.id)                      as numberOfBooking, " +
                    "       coalesce(sum(a.total_cost), 0) as totalCost, " +
                    "       a.user_id                        as userId, " +
                    "       b.full_name                      as fullName " +
                    "from book_home a " +
                    "         inner join user b " +
                    "where a.user_id = b.userid " +
                    "group by a.user_id, b.full_name "
    )
    Page<InfoUserBookingProjection> getPageStatisticInfoUserBooking(Pageable pageable);
}
