package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.AmenityCategories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AmenityCategoriesRepository extends JpaRepository<AmenityCategories, UUID> {


    @Query(
            nativeQuery = true,
            value = "select a.* " +
                    "from amenity_categories a " +
                    "where (a.is_default = :isDefault " +
                    "   or :isDefault is null)  " +
                    "  and (:keyword is null or upper(a.name) like upper(Concat('%', :keyword, '%'))) ",
            countQuery = "select a.id " +
                    "from amenity_categories a " +
                    "where a.is_default = :isDefault " +
                    "   or :isDefault is null " +
                    "  and (:keyword is null or upper(a.name) like upper(Concat('%', :keyword, '%'))) "
    )
    Page<AmenityCategories> findPageWithFilter(String keyword, Boolean isDefault, Pageable pageable);


    @Query(
            nativeQuery = true,
            value = "select IF(count(a.id) > 0, 'false', 'true') " +
                    "from amenities a where a.deleted is false and a.category_id = :categoryId "
    )
    boolean checkCanDelete(@Param("categoryId") UUID categoryId);
}
