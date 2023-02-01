package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.RoomCategories;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomCategoriesRepository extends JpaRepository<RoomCategories, Long> {
    Optional<RoomCategories> findByRoomCategoryId(UUID roomCategoryId);


    @Query(nativeQuery = true,
            value = "select a.*  " +
                    "from room_categories a  " +
                    "where a.important = :important  " +
                    "   or :important is null ",
            countQuery = "select a.id  " +
                    "from room_categories a  " +
                    "where a.important = :important  " +
                    "   or :important is null ")
    Page<RoomCategories> findPageWithFilter(@Param(":important") Boolean important, Pageable pageable);
}
