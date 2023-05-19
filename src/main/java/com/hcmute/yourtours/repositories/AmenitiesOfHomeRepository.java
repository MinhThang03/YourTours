package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.AmenitiesOfHome;
import com.hcmute.yourtours.models.amenities_of_home.projection.AmenityOfHomeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AmenitiesOfHomeRepository extends JpaRepository<AmenitiesOfHome, UUID> {

    @Query(nativeQuery = true,
            value = "select b.id  as amenityId, " +
                    "       c.home_id     as homeId, " +
                    "       b.name        as name, " +
                    "       b.description as description, " +
                    "       b.status      as status, " +
                    "       c.is_have     as isHave " +
                    "from amenity_categories a " +
                    "         inner join amenities b on a.id = b.category_id and b.deleted is false " +
                    "         left join (select a.* " +
                    "                    from amenities_of_home a " +
                    "                    where a.home_id = :homeId) c on b.id = c.amenity_id " +
                    "where a.id = :categoryId " +
                    "order by b.name ")
    List<AmenityOfHomeProjection> findAllByAmenityCategoryIdAndHomeId(@Param("categoryId") UUID categoryId,
                                                                      @Param("homeId") UUID homeId);


    Optional<AmenitiesOfHome> findByHomeIdAndAmenityId(UUID homeId, UUID amenityId);
}
