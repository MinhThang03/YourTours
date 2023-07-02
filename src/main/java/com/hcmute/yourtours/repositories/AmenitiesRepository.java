package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.Amenities;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AmenitiesRepository extends JpaRepository<Amenities, UUID> {

    @Query(
            nativeQuery = true,
            value = "select a.*  " +
                    "from amenities a  " +
                    "         inner join amenity_categories b on a.category_id = b.id  " +
                    "where (b.id = :categoryId  " +
                    "   or :categoryId is null) and a.deleted is false " +
                    "  and (:keyword is null or upper(a.name) like upper(Concat('%', :keyword, '%'))) ",
            countQuery = "select a.id  " +
                    "from amenities a  " +
                    "         inner join amenity_categories b on a.category_id = b.id  " +
                    "where (b.id = :categoryId  " +
                    "   or :categoryId is null) and a.deleted is false " +
                    "  and (:keyword is null or upper(a.name) like upper(Concat('%', :keyword, '%'))) "
    )
    Page<Amenities> getPageWithAmenityFilter(
            @Param("keyword") String keyword,
            @Param("categoryId") UUID categoryId,
            Pageable pageable
    );


    @Query(
            nativeQuery = true,
            value = "select a.* " +
                    "from amenities a, " +
                    "     amenities_of_home b " +
                    "where a.id = b.amenity_id " +
                    "  and b.home_id = :homeId " +
                    "  and a.deleted is false " +
                    "limit :limit "
    )
    List<Amenities> getLimitByHomeId(@Param("homeId") UUID homeId,
                                     @Param("limit") Integer limit);


    @Query(
            nativeQuery = true,
            value = "select a.* " +
                    "from amenities a, " +
                    "     amenities_of_home b " +
                    "where a.id = b.amenity_id and a.deleted is false " +
                    "  and b.home_id = :homeId "
    )
    List<Amenities> getByByHomeId(@Param("homeId") UUID homeId);


    @Query(
            nativeQuery = true,
            value = "select a.*  " +
                    "from amenities a  " +
                    "where a.set_filter is true  and a.deleted is false " +
                    "limit :offset , :limit "
    )
    List<Amenities> getLimitSetFilter(@Param("offset") Integer offset,
                                      @Param("limit") Integer limit);


    @Query(
            nativeQuery = true,
            value = "select  count(a.id)  " +
                    "from amenities a  " +
                    "where a.set_filter = 'true'  and a.deleted is false "
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


    @Query(
            nativeQuery = true,
            value = "select IF(count(a.id) > 0, 'true', 'false')   " +
                    "from amenities_of_home a   " +
                    "where a.deleted is false   " +
                    "  and a.amenity_id = :amenityId "
    )
    boolean existsForeignKey(@Param("amenityId") UUID amenityId);


    @Modifying
    @Query(
            nativeQuery = true,
            value = "update amenities a    " +
                    "set a.deleted = true    " +
                    "where a.id = :amenityId  "
    )
    void softDelete(@Param("amenityId") UUID amenityId);
}
