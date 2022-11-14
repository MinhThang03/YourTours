package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.AmenitiesOfHomeCommand;
import com.hcmute.yourtours.models.amenities_of_home.projection.AmenityOfHomeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AmenitiesOfHomeRepository extends JpaRepository<AmenitiesOfHomeCommand, Long> {
    Optional<AmenitiesOfHomeCommand> findByAmenityOfHomeId(UUID amenityOfHomeId);

    @Query(nativeQuery = true,
            value = "select b.amenity_id  as amenityId, " +
                    "       c.home_id     as homeId, " +
                    "       b.name        as name, " +
                    "       b.description as description, " +
                    "       b.status      as status, " +
                    "       c.is_have     as isHave " +
                    "from amenity_categories a " +
                    "         inner join amenities b on a.amenity_category_id = b.category_id " +
                    "         left join (select a.* " +
                    "                    from amenities_of_home a " +
                    "                    where a.home_id = :homeId) c on b.amenity_id = c.amenity_id " +
                    "where a.amenity_category_id = :categoryId " +
                    "sort by b.name ")
    List<AmenityOfHomeProjection> findAllByAmenityCategoryIdAndHomeId(@Param("categoryId") UUID categoryId,
                                                                      @Param("homeId") UUID homeId);

    @Modifying
    void deleteAllByHomeId(UUID homeId);

    @Modifying
    void deleteByHomeIdAndAmenityId(UUID homeId, UUID amenityId);

    Optional<AmenitiesOfHomeCommand> findByHomeIdAndAmenityId(UUID homeId, UUID amenityId);
}
