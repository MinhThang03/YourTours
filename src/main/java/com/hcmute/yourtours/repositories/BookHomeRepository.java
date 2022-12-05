package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.BookHomesCommand;
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
public interface BookHomeRepository extends JpaRepository<BookHomesCommand, Long> {
    Optional<BookHomesCommand> findByBookId(UUID bookId);

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
    Optional<BookHomesCommand> findOneByBetweenDate(@Param("date") LocalDate date,
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
                    "order by a.date_start desc "
    )
    Page<BookHomesCommand> findAllByCmsFilter(@Param("status") String status,
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
    Page<BookHomesCommand> findBookingOfUser(@Param("customerId") UUID customerId,
                                             Pageable pageable);


    @Query(
            nativeQuery = true,
            value = "select a.* " +
                    "from book_home a " +
                    "where DATE(a.date_end) < DATE(:date) " +
                    "  and a.status <> :status "
    )
    List<BookHomesCommand> findAllCommandNeedUpdateCheckOut(@Param("date") LocalDate date,
                                                            @Param("status") String status);


    @Query(
            nativeQuery = true,
            value = "select count(a.id) " +
                    "from book_home a, " +
                    "     owner_of_home b " +
                    "where a.home_id = b.home_id " +
                    "  and b.user_id = :ownerId "
    )
    Long countTotalBookingOfOwner(UUID ownerId);

    @Query(
            nativeQuery = true,
            value = "select count(a.id)  " +
                    "from book_home a,  " +
                    "     owner_of_home b  " +
                    "where a.home_id = b.home_id  " +
                    "  and b.user_id = :ownerId  " +
                    "  and a.status = :status "
    )
    Long countTotalBookingOfOwnerFinish(UUID ownerId, String status);

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
                    "group by c.name, a.home_id "
    )
    List<HomeBookingStatisticProjection> getHomeBookingStatisticWithOwner(UUID ownerId);


    @Query(
            nativeQuery = true,
            value = "select coalesce(sum(a.total_cost), 0)   " +
                    "from book_home a,   " +
                    "     owner_of_home b   " +
                    "where a.home_id = b.home_id   " +
                    "  and b.user_id = :ownerId   " +
                    "  and a.status = :status   " +
                    "  and MONTH(a.date_start) = :month   " +
                    "  and YEAR(a.date_start) = :year "
    )
    Double getRevenueWithOwnerIdAndYear(UUID ownerId, String status, Integer month, Integer year);
}
