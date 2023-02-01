package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.RoomsOfHome;
import com.hcmute.yourtours.models.rooms_of_home.projections.NumberOfRoomsProjections;
import com.hcmute.yourtours.models.rooms_of_home.projections.RoomOfHomeDetailWithBedProjections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomsOfHomeRepository extends JpaRepository<RoomsOfHome, Long> {
    Optional<RoomsOfHome> findByRoomOfHomeId(UUID roomOfHomeId);


    @Query(
            nativeQuery = true,
            value = "select count(a.id)        as number,   " +
                    "       a.room_category_id as roomCategoryId,   " +
                    "       b.name             as roomCategoryName   " +
                    "from rooms_of_home a,   " +
                    "     room_categories b   " +
                    "where a.home_id = :homeId   " +
                    "  and a.room_category_id = b.room_category_id   " +
                    "  and (b.important = :important or :important is null)   " +
                    "group by a.room_category_id "
    )
    List<NumberOfRoomsProjections> getNumberOfRoomCategoryByHomeId(@Param("homeId") UUID homeId,
                                                                   @Param("important") Boolean important);


    @Query(
            nativeQuery = true,
            value = "select a.* " +
                    "from rooms_of_home a " +
                    "where a.home_id = :homeId " +
                    "   or :homeId is null " +
                    "order by a.name ",
            countQuery = "select a.id " +
                    "from rooms_of_home a " +
                    "where a.home_id = :homeId " +
                    "   or :homeId is null "
    )
    Page<RoomsOfHome> getPageWithFilter(@Param("homeId") UUID homeId,
                                        Pageable pageable);

    @Query(
            nativeQuery = true,
            value = "select a.* " +
                    "from rooms_of_home a " +
                    "where a.home_id = :homeId " +
                    "   or :homeId is null " +
                    "order by a.name "
    )
    List<RoomsOfHome> getListWithFilter(@Param("homeId") UUID homeId);


    @Query(
            nativeQuery = true,
            value = "select a.* " +
                    "from rooms_of_home a " +
                    "where a.home_id = :homeId " +
                    "  and a.room_category_id = :categoryId " +
                    "order by a.order_flag desc " +
                    "limit 1 "
    )
    Optional<RoomsOfHome> findByHomeIdAndCategoryIdWithMaxOrder(@Param("homeId") UUID homeId,
                                                                @Param("categoryId") UUID categoryId);

    Long countAllByHomeIdAndCategoryId(UUID homeId, UUID categoryId);


    @Query(
            nativeQuery = true,
            value = "select a.name            as roomName,   " +
                    "       a.room_of_home_id as roomHomeId,   " +
                    "       b.amount          as numberOfBed,   " +
                    "       c.name            as nameOfBed   " +
                    "from rooms_of_home a,   " +
                    "     beds_of_home b,   " +
                    "     bed_categories c   " +
                    "where a.room_of_home_id = b.room_of_home_id   " +
                    "  and b.bed_category_id = c.bed_category_id   " +
                    "  and a.home_id = :homeId   " +
                    "  and b.amount is not null"
    )
    List<RoomOfHomeDetailWithBedProjections> getRoomHaveConfigBed(@Param("homeId") UUID homeId);

    List<RoomsOfHome> findAllByHomeIdAndCategoryId(UUID homeId, UUID categoryId);
}
