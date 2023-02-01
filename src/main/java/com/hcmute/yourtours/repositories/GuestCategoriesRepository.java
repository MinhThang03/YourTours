package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.GuestCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GuestCategoriesRepository extends JpaRepository<GuestCategories, Long> {
    Optional<GuestCategories> findByGuestCategoryId(UUID guestCategoryID);
}
