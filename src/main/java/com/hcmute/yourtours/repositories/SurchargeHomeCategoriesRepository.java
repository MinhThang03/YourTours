package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.SurchargeHomeCategories;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SurchargeHomeCategoriesRepository extends JpaRepository<SurchargeHomeCategories, UUID> {

    @Query(
            nativeQuery = true,
            value = "select IF(count(a.id) > 0, 'true', 'false')         " +
                    "from surcharges_of_home a         " +
                    "where a.deleted is false         " +
                    "  and a.surcharge_category_id = :categoryId "
    )
    boolean existForeignKey(@Param("categoryId") UUID categoryId);


    @Modifying
    @Query(
            nativeQuery = true,
            value = "update surcharge_home_categories a        " +
                    "set a.deleted = true        " +
                    "where a.id = :categoryId         "
    )
    void softDelete(@Param("categoryId") UUID categoryId);

    @Override
    @Query(
            value = "SELECT a FROM SurchargeHomeCategories a " +
                    "WHERE a.deleted is false ",
            countQuery = "SELECT a.id FROM SurchargeHomeCategories a " +
                    "WHERE a.deleted is false "
    )
    Page<SurchargeHomeCategories> findAll(Pageable pageable);


    @Query(
            value = "SELECT a FROM SurchargeHomeCategories a " +
                    "WHERE a.deleted is false   " +
                    "AND (:keyword is null or upper(a.name) like upper(Concat('%', :keyword, '%')))",
            countQuery = "SELECT a.id FROM SurchargeHomeCategories a " +
                    "WHERE a.deleted is false " +
                    " AND (:keyword is null or upper(a.name) like upper(Concat('%', :keyword, '%'))) "
    )
    Page<SurchargeHomeCategories> findAllWithFilter(String keyword, Pageable pageable);
}
