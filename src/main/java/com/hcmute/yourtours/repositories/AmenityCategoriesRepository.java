package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.AmenityCategoriesCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AmenityCategoriesRepository extends JpaRepository<AmenityCategoriesCommand, Long> {
    Optional<AmenityCategoriesCommand> findByAmenityCategoryId(UUID amenityCategoryId);

    Boolean existsByAmenityCategoryId(UUID amenityCategoryId);

    @Query(nativeQuery = true,
            value = "select a.* " +
                    "from amenity_categories a " +
                    "where a.is_default = :isDefault " +
                    "   or :isDefault is null",
            countQuery = "select a.id " +
                    "from amenity_categories a " +
                    "where a.is_default = :isDefault " +
                    "   or :isDefault is null ")
    Page<AmenityCategoriesCommand> findPageWithFilter(Boolean isDefault, Pageable pageable);
}
