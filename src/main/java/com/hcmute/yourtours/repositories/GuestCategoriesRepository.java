package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.GuestCategoriesCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GuestCategoriesRepository extends JpaRepository<GuestCategoriesCommand, Long> {
    Optional<GuestCategoriesCommand> findByGuestCategoryId(UUID guestCategoryID);
}
