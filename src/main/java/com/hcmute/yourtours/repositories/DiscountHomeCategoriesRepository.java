package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.DiscountHomeCategories;
import com.hcmute.yourtours.entities.SurchargeHomeCategories;
import com.hcmute.yourtours.enums.CommonStatusEnum;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DiscountHomeCategoriesRepository extends JpaRepository<DiscountHomeCategories, UUID> {

    @Query(
            value = "select a from DiscountHomeCategories a " +
                    "where a.deleted is false " +
                    "and a.status = :status"
    )
    List<DiscountHomeCategories> findAllByStatus(CommonStatusEnum status);


    @Query(
            nativeQuery = true,
            value = "select IF(count(a.id) > 0, 'true', 'false')      " +
                    "from discount_of_home a      " +
                    "where a.deleted is false      " +
                    "  and a.discount_category_id = :categoryId "
    )
    boolean existForeignKey(@Param("categoryId") UUID categoryId);


    @Modifying
    @Query(
            nativeQuery = true,
            value = "update discount_home_categories a      " +
                    "set a.deleted = true      " +
                    "where a.id = :categoryId "
    )
    SurchargeHomeCategories softDelete(@Param("categoryId") UUID categoryId);


}
