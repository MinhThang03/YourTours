package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.DiscountOfHome;
import com.hcmute.yourtours.models.discount_of_home.projections.NotificationDiscountProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiscountOfHomeRepository extends JpaRepository<DiscountOfHome, UUID> {

    Optional<DiscountOfHome> findByHomeIdAndCategoryId(UUID homeId, UUID categoryId);

    List<DiscountOfHome> findAllByHomeIdAndCategoryId(UUID homeId, UUID categoryId);


    @Query(
            nativeQuery = true,
            value = "select c.id        as homeId, " +
                    "       c.name      as homeName, " +
                    "       c.thumbnail as thumbnail, " +
                    "       d.user_id   as userId, " +
                    "       b.name      as discountName, " +
                    "       a.percent   as percent " +
                    "from discount_of_home a, " +
                    "     discount_home_categories b, " +
                    "     homes c, " +
                    "     item_favorites d " +
                    "where a.discount_category_id = b.id " +
                    "  and a.home_id = c.id " +
                    "  and c.id = d.home_id " +
                    "  and a.id = :id " +
                    "  and a.percent is not null  "
    )
    List<NotificationDiscountProjection> getListNotificationDiscount(@Param("id") UUID id);
}
