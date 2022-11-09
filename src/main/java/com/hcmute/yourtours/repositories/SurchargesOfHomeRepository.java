package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.SurchargesOfHomeCommand;
import com.hcmute.yourtours.models.surcharges_of_home.projections.SurchargeHomeViewProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SurchargesOfHomeRepository extends JpaRepository<SurchargesOfHomeCommand, Long> {
    Optional<SurchargesOfHomeCommand> findBySurchargeOfHomeId(UUID surchargeOfHomeId);


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

    @Modifying
    void deleteAllByHomeIdAndCategoryId(UUID homeId, UUID categoryId);
}
