package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.AmenitiesCommand;
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
public interface AmenitiesRepository extends JpaRepository<AmenitiesCommand, Long> {
    Optional<AmenitiesCommand> findByAmenityId(UUID amenityId);

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
    Page<AmenitiesCommand> getPageWithAmenityFilter(@Param("categoryId") UUID categoryId,
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
    List<AmenitiesCommand> getLimitByHomeId(@Param("homeId") UUID homeId,
                                            @Param("limit") Integer limit);


    @Query(
            nativeQuery = true,
            value = "select a.*  " +
                    "from amenities a  " +
                    "where a.set_filter = 'true'  " +
                    "limit :offset , :limit "
    )
    List<AmenitiesCommand> getLimitSetFilter(@Param("offset") Integer offset,
                                             @Param("limit") Integer limit);


    @Query(
            nativeQuery = true,
            value = "select  count(a.id)  " +
                    "from amenities a  " +
                    "where a.set_filter = 'true'  "
    )
    Long countLimitSetFilter();
}
