package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.SurchargesOfHome;
import com.hcmute.yourtours.models.surcharges_of_home.projections.SurchargeHomeViewProjection;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SurchargesOfHomeRepository extends JpaRepository<SurchargesOfHome, UUID> {


    @Query(
            nativeQuery = true,
            value = "select a.surcharge_category_id as surchargeCategoryId,  " +
                    "       a.name                  as surchargeCategoryName,  " +
                    "       a.description           as description,  " +
                    "       b.cost                  as cost  " +
                    "from surcharge_home_categories a  " +
                    "         left join (select a.* from surcharges_of_home a where a.home_id = :homeId) b  " +
                    "                   on a.surcharge_category_id = b.surcharge_category_id  " +
                    "where a.status = 'ACTIVE' "
    )
    List<SurchargeHomeViewProjection> getListCategoryWithHomeId(UUID homeId);

    List<SurchargesOfHome> findAllByHomeIdAndCategoryId(UUID homeId, UUID categoryId);

    Optional<SurchargesOfHome> findByHomeIdAndCategoryId(UUID homeId, UUID categoryId);

    @Query(
            nativeQuery = true,
            value = "select b.surcharge_category_id as surchargeCategoryId,   " +
                    "       b.name                  as surchargeCategoryName,   " +
                    "       b.description           as description,   " +
                    "       a.cost                  as cost   " +
                    "from surcharges_of_home a,   " +
                    "     surcharge_home_categories b   " +
                    "where b.status = 'ACTIVE'   " +
                    "  and a.surcharge_category_id = b.surcharge_category_id   " +
                    "  and a.cost is not null  " +
                    "  and a.home_id = :homeId ",
            countQuery = "select b.id    " +
                    "from surcharges_of_home a,    " +
                    "     surcharge_home_categories b    " +
                    "where b.status = 'ACTIVE'    " +
                    "  and a.surcharge_category_id = b.surcharge_category_id    " +
                    "  and a.cost is not null  " +
                    "  and a.home_id = :homeId "
    )
    Page<SurchargeHomeViewProjection> getPageByHomeId(@Param("homeId") UUID homeId,
                                                      Pageable pageable);


    @Query(
            nativeQuery = true,
            value = "select b.id as surchargeCategoryId,   " +
                    "       b.name                  as surchargeCategoryName,   " +
                    "       b.description           as description,   " +
                    "       a.cost                  as cost   " +
                    "from surcharges_of_home a,   " +
                    "     surcharge_home_categories b   " +
                    "where b.status = 'ACTIVE'   " +
                    "  and a.surcharge_category_id = b.id   " +
                    "  and a.cost is not null  " +
                    "  and a.home_id = :homeId "
    )
    List<SurchargeHomeViewProjection> getListByHomeId(@Param("homeId") UUID homeId);
}
