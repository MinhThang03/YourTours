package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.Amenities;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AmenitiesRepository extends JpaRepository<Amenities, UUID> {

    @Query(nativeQuery = true,
            value = "select a.*  " +
                    "from amenities a  " +
                    "         inner join amenity_categories b on a.category_id = b.amenity_category_id  " +
                    "where b.amenity_category_id = :categoryId  " +
                    "   or :categoryId is null",
            countQuery = "select a.id  " +
                    "from amenities a  " +
                    "         inner join amenity_categories b on a.category_id = b.amenity_category_id  " +
                    "where b.amenity_category_id = :categoryId  " +
                    "   or :categoryId is null ")
    Page<Amenities> getPageWithAmenityFilter(@Param("categoryId") UUID categoryId,
                                             Pageable pageable);


    @Query(
            nativeQuery = true,
            value = "select a.* " +
                    "from amenities a, " +
                    "     amenities_of_home b " +
                    "where a.amenity_id = b.amenity_id " +
                    "  and b.home_id = :homeId " +
                    "limit :limit "
    )
    List<Amenities> getLimitByHomeId(@Param("homeId") UUID homeId,
                                     @Param("limit") Integer limit);


    @Query(
            nativeQuery = true,
            value = "select a.* " +
                    "from amenities a, " +
                    "     amenities_of_home b " +
                    "where a.amenity_id = b.amenity_id " +
                    "  and b.home_id = :homeId "
    )
    List<Amenities> getByByHomeId(@Param("homeId") UUID homeId);


    @Query(
            nativeQuery = true,
            value = "select a.*  " +
                    "from amenities a  " +
                    "where a.set_filter is true  " +
                    "limit :offset , :limit "
    )
    List<Amenities> getLimitSetFilter(@Param("offset") Integer offset,
                                      @Param("limit") Integer limit);


    @Query(
            nativeQuery = true,
            value = "select  count(a.id)  " +
                    "from amenities a  " +
                    "where a.set_filter = 'true'  "
    )
    Long countLimitSetFilter();

    @Query(
            nativeQuery = true,
            value = "select a.is_have " +
                    "from amenities_of_home a " +
                    "where a.home_id = :homeId " +
                    "  and a.amenity_id = :amenityId "
    )
    Boolean getConfigByHomeIdAndCategoryId(UUID homeId, UUID amenityId);
}
