package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.RoomsOfHomeCommand;
import com.hcmute.yourtours.models.rooms_of_home.projections.NumberOfRoomsProjections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomsOfHomeRepository extends JpaRepository<RoomsOfHomeCommand, Long> {
    Optional<RoomsOfHomeCommand> findByRoomOfHomeId(UUID roomOfHomeId);

    @Modifying
    void deleteAllByHomeId(UUID homeId);

    @Query(
            nativeQuery = true,
            value = "select count(a.id) as number, " +
                    "       a.room_category_id as roomCategoryId, " +
                    "       b.name as roomCategoryName " +
                    "from rooms_of_home a, " +
                    "     room_categories b " +
                    "where a.home_id = :homeId " +
                    "  and a.room_category_id = b.room_category_id " +
                    "group by a.room_category_id "
    )
    List<NumberOfRoomsProjections> getNumberOfRoomCategoryByHomeId(@Param("homeId") UUID homeId);
}
