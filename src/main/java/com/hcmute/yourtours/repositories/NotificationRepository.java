package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    boolean existsByUserIdAndView(UUID userId, boolean view);

    boolean existsByUserId(UUID userId);

    @Modifying
    void deleteAllByUserIdAndView(UUID userId, boolean view);

    @Modifying
    void deleteAllByUserId(UUID userId);

}
