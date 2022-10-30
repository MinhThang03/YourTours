package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.AmenitiesCommand;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
}
