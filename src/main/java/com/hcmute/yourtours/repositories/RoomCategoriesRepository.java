package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.RoomCategories;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoomCategoriesRepository extends JpaRepository<RoomCategories, UUID> {


    @Query(nativeQuery = true,
            value = "select a.*  " +
                    "from room_categories a  " +
                    "where (a.important = :important  " +
                    "   or :important is null) " +
                    "and a.deleted is false ",
            countQuery = "select a.id  " +
                    "from room_categories a  " +
                    "where (a.important = :important  " +
                    "   or :important is null) " +
                    "and a.deleted is false ")
    Page<RoomCategories> findPageWithFilter(@Param(":important") Boolean important, Pageable pageable);


    @Query(
            nativeQuery = true,
            value = "select IF(count(a.id) > 0, 'true', 'false')       " +
                    "from rooms_of_home a       " +
                    "where a.deleted is false       " +
                    "  and a.room_category_id = :categoryId "
    )
    boolean existForeignKey(@Param("categoryId") UUID categoryId);

    @Modifying
    @Query(
            nativeQuery = true,
            value = "update room_categories a        " +
                    "set a.deleted = true        " +
                    "where a.id = :categoryId "
    )
    RoomCategories softDelete(@Param("categoryId") UUID categoryId);
}
