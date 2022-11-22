package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.BookHomesCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
}
