package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.OwnerOfHome;
import com.hcmute.yourtours.enums.CommonStatusEnum;
import com.hcmute.yourtours.models.owner_of_home.projections.StatisticInfoOwnerProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OwnerOfHomesRepository extends JpaRepository<OwnerOfHome, UUID> {

    boolean existsByUserIdAndHomeId(UUID userId, UUID homeId);

    @Query(
            nativeQuery = true,
            value = "select b.full_name as ownerName   " +
                    "from owner_of_home a,   " +
                    "     user b   " +
                    "where a.user_id = b.id   " +
                    "  and a.is_main_owner is true   " +
                    "  and a.home_id = :homeId  "
    )
    String getMainOwnerNameOfHome(UUID homeId);


    @Query(
            nativeQuery = true,
            value = "select coalesce(a.numberOfHomes, 0)   as numberOfHomes,     " +
                    "       a.userId                       as userId,     " +
                    "       a.fullName                     as fullName,     " +
                    "       a.email                        as email,     " +
                    "       coalesce(b.numberOfBooking, 0) as numberOfBooking,     " +
                    "       coalesce(b.totalCost, 0)       as totalCost     " +
                    "from (select count(a.id) as numberOfHomes,     " +
                    "             a.user_id   as userId,     " +
                    "             b.full_name as fullName,     " +
                    "             b.email     as email     " +
                    "      from owner_of_home a,     " +
                    "           user b     " +
                    "      where a.is_main_owner is true     " +
                    "        and a.user_id = b.id     " +
                    "      group by a.user_id) a     " +
                    "         left join     " +
                    "     " +
                    "     (select c.id                             as userId,     " +
                    "             count(a.id)                      as numberOfBooking,     " +
                    "             coalesce(sum(a.cost_of_host), 0) as totalCost     " +
                    "      from book_home a,     " +
                    "           owner_of_home b,     " +
                    "           user c     " +
                    "      where a.home_id = b.home_id     " +
                    "        and c.id = b.user_id     " +
                    "        and a.status = 'CHECK_OUT'     " +
                    "        and DATE(a.created_date) between :dateStart and :dateEnd     " +
                    "      group by c.id, c.full_name) b     " +
                    "     on a.userId = b.userId     " +
                    "order by coalesce(b.totalCost, 0) desc ",
            countQuery = "select coalesce(a.numberOfHomes, 0)   as numberOfHomes,     " +
                    "       a.userId                       as userId,     " +
                    "       a.fullName                     as fullName,     " +
                    "       a.email                        as email,     " +
                    "       coalesce(b.numberOfBooking, 0) as numberOfBooking,     " +
                    "       coalesce(b.totalCost, 0)       as totalCost     " +
                    "from (select count(a.id) as numberOfHomes,     " +
                    "             a.user_id   as userId,     " +
                    "             b.full_name as fullName,     " +
                    "             b.email     as email     " +
                    "      from owner_of_home a,     " +
                    "           user b     " +
                    "      where a.is_main_owner is true     " +
                    "        and a.user_id = b.id     " +
                    "      group by a.user_id) a     " +
                    "         left join     " +
                    "     " +
                    "     (select c.id                             as userId,     " +
                    "             count(a.id)                      as numberOfBooking,     " +
                    "             coalesce(sum(a.cost_of_host), 0) as totalCost     " +
                    "      from book_home a,     " +
                    "           owner_of_home b,     " +
                    "           user c     " +
                    "      where a.home_id = b.home_id     " +
                    "        and c.id = b.user_id     " +
                    "        and a.status = 'CHECK_OUT'     " +
                    "        and DATE(a.created_date) between :dateStart and :dateEnd     " +
                    "      group by c.id, c.full_name) b     " +
                    "     on a.userId = b.userId     " +
                    "order by coalesce(b.totalCost, 0) desc "
    )
    Page<StatisticInfoOwnerProjection> getStatisticInfoOwner(LocalDate dateStart, LocalDate dateEnd, Pageable pageable);

    Optional<OwnerOfHome> findByHomeIdAndIsMainOwner(UUID homeId, Boolean isMainOwner);


    @Query(
            value = "select c.status from Homes a, OwnerOfHome b, User c " +
                    "where a.id = b.homeId " +
                    "and b.userId = c.id " +
                    "and b.isMainOwner is true  "
    )
    CommonStatusEnum getStatusOfOwner(UUID homeId);
}
