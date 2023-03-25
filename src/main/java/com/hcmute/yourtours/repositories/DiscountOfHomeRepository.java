package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.DiscountOfHome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiscountOfHomeRepository extends JpaRepository<DiscountOfHome, UUID> {

    Optional<DiscountOfHome> findByHomeIdAndCategoryId(UUID homeId, UUID categoryId);

    @Modifying
    void deleteAllByHomeIdAndCategoryId(UUID homeId, UUID categoryId);

    List<DiscountOfHome> findAllByHomeIdAndCategoryId(UUID homeId, UUID categoryId);
}
