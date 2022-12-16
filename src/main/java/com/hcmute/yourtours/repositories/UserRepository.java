package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.UserCommand;
import com.hcmute.yourtours.models.statistic.admin.projections.StatisticCountProjections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserCommand, Long> {

    Optional<UserCommand> findByUserId(UUID userId);

    Boolean existsByEmail(String email);

    Optional<UserCommand> findByEmail(String email);

    @Query(
            nativeQuery = true,
            value = "select a.* " +
                    "from user a, " +
                    "     owner_of_home b " +
                    "where a.userid = b.user_id " +
                    "  and a.userid = :userId " +
                    "limit 1 "
    )
    Optional<UserCommand> findByUserIdAndOwner(UUID userId);

    @Query(
            value = "select a from UserCommand  a order by a.createdDate desc ",
            countQuery = "select a.id from UserCommand  a order by a.createdDate desc "
    )
    Page<UserCommand> getAll(PageRequest pageRequest);

    @Query(
            nativeQuery = true,
            value = "select a.numberOfGuests  as numberOfGuests," +
                    "       b.numberOfOwner   as numberOfOwner," +
                    "       c.numberOfBooking as numberOfBooking," +
                    "       a.totalCost       as totalCost" +
                    "from" +
                    "     (select count(distinct a.user_id) as numberOfOwner from owner_of_home a) b," +
                    "     (select count(a.id) as numberOfBooking from book_home a) c," +
                    "     (select coalesce(sum(coalesce(a.cost_of_admin, 0)), 0) as totalCost," +
                    "             count(distinct (a.user_id))                    as numberOfGuests" +
                    "      from book_home a" +
                    "      where a.status = 'CHECK_OUT') a "
    )
    StatisticCountProjections getAdminStatisticCount();
}
