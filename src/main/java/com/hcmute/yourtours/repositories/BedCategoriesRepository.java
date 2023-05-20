package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.BedCategories;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BedCategoriesRepository extends JpaRepository<BedCategories, UUID> {

    @Query(
            nativeQuery = true,
            value = "select IF(count(a.id) > 0, 'true', 'false')    " +
                    "from beds_of_home a    " +
                    "where a.deleted is false    " +
                    "  and a.bed_category_id = :categoryId "
    )
    boolean existForeignKey(@Param("categoryId") UUID categoryId);

    @Modifying
    @Query(
            nativeQuery = true,
            value = "update bed_categories a      " +
                    "set a.deleted = true      " +
                    "where a.id = :categoryId "
    )
    BedCategories softDelete(@Param("categoryId") UUID categoryId);


    @Override
    @Query(
            value = "SELECT a FROM BedCategories a " +
                    "WHERE a.deleted is false ",
            countQuery = "SELECT a.id FROM BedCategories a " +
                    "WHERE a.deleted is false "
    )
    Page<BedCategories> findAll(Pageable pageable);
}
