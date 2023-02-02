package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.OwnerOfHome;
import com.hcmute.yourtours.models.owner_of_home.projections.StatisticInfoOwnerProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OwnerOfHomesRepository extends JpaRepository<OwnerOfHome, UUID> {

    boolean existsByUserIdAndHomeId(UUID userId, UUID homeId);

    @Query(
            nativeQuery = true,
            value = "select b.full_name as ownerName   " +
                    "from owner_of_home a,   " +
                    "     user b   " +
                    "where a.user_id = b.userid   " +
                    "  and a.is_main_owner is true   " +
                    "  and a.home_id = :homeId  "
    )
    String getMainOwnerNameOfHome(UUID homeId);


    @Query(
            nativeQuery = true,
            value = "select a.numberOfHomes as numberOfHomes, " +
                    "       a.userId as userId, " +
                    "       b.fullName as fullName, " +
                    "       b.numberOfBooking as numberOfBooking, " +
                    "       b.totalCost as totalCost " +
                    "from (select count(a.id) as numberOfHomes, " +
                    "             a.user_id   as userId " +
                    "      from owner_of_home a " +
                    "      group by a.user_id) a, " +
                    " " +
                    "     (select c.userid                         as userId, " +
                    "             c.full_name                      as fullName, " +
                    "             count(a.id)                      as numberOfBooking, " +
                    "             coalesce(sum(a.cost_of_host), 0) as totalCost " +
                    "      from book_home a, " +
                    "           owner_of_home b, " +
                    "           user c " +
                    "      where a.home_id = b.home_id " +
                    "        and c.userid = b.user_id " +
                    "      group by c.userid, c.full_name) b " +
                    "where a.userId = b.userId ",
            countQuery = "select a.numberOfHomes as numberOfHomes, " +
                    "       a.userId as userId, " +
                    "       b.fullName as fullName, " +
                    "       b.numberOfBooking as numberOfBooking, " +
                    "       b.totalCost as totalCost " +
                    "from (select count(a.id) as numberOfHomes, " +
                    "             a.user_id   as userId " +
                    "      from owner_of_home a " +
                    "      group by a.user_id) a, " +
                    " " +
                    "     (select c.userid                         as userId, " +
                    "             c.full_name                      as fullName, " +
                    "             count(a.id)                      as numberOfBooking, " +
                    "             coalesce(sum(a.cost_of_host), 0) as totalCost " +
                    "      from book_home a, " +
                    "           owner_of_home b, " +
                    "           user c " +
                    "      where a.home_id = b.home_id " +
                    "        and c.userid = b.user_id " +
                    "      group by c.userid, c.full_name) b " +
                    "where a.userId = b.userId "
    )
    Page<StatisticInfoOwnerProjection> getStatisticInfoOwner(Pageable pageable);
}
