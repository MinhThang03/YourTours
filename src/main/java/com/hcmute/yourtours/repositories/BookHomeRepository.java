package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.BookHomesCommand;
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
                    "limit 1 "
    )
    Optional<BookHomesCommand> findOneByBetweenDate(@Param("date") LocalDate date,
                                                    @Param("homeId") UUID homeId);
}
