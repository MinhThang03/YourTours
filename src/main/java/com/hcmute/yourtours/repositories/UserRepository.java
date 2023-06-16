package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.User;
import com.hcmute.yourtours.models.statistic.admin.projections.StatisticCountProjections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {


    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query(
            nativeQuery = true,
            value = "select a.* " +
                    "from user a, " +
                    "     owner_of_home b " +
                    "where a.id = b.user_id " +
                    "  and a.id = :userId " +
                    "limit 1 "
    )
    Optional<User> findByUserIdAndOwner(UUID userId);

    @Query(
            nativeQuery = true,
            value = "select a.*   " +
                    "from User a   " +
                    "where (:keyword is null   " +
                    "    or upper(a.full_name) like upper(Concat('%', :keyword, '%'))   " +
                    "    or a.email = :keyword)   " +
                    "order by a.created_date desc     ",
            countQuery = "select count(a.id)    " +
                    "from User a   " +
                    "where (:keyword is null   " +
                    "    or upper(a.full_name) like upper(Concat('%', :keyword, '%'))   " +
                    "    or a.email = :keyword)   " +
                    "order by a.created_date desc    "
    )
    Page<User> getAll(String keyword, Pageable pageable);


    @Query(
            nativeQuery = true,
            value = "select a.numberOfGuests  as numberOfGuests, " +
                    "       b.numberOfOwner   as numberOfOwner, " +
                    "       c.numberOfBooking as numberOfBooking, " +
                    "       d.totalCost       as totalCost " +
                    "from (select count(a.userId) as numberOfGuests " +
                    "      from (select distinct a.user_id as userId " +
                    "            from book_home a) a) a, " +
                    "     (select count(a.userId) as numberOfOwner " +
                    "      from (select distinct a.user_id as userId from owner_of_home a) a) b, " +
                    "     (select count(a.id) as numberOfBooking from book_home a) c, " +
                    "     (select coalesce(sum(coalesce(a.cost_of_admin, 0)), 0) as totalCost " +
                    "      from book_home a " +
                    "      where a.status = 'CHECK_OUT') d "
    )
    StatisticCountProjections getAdminStatisticCount();
}
