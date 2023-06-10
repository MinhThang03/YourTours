package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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

    @Query(
            value = "SELECT a FROM Notification a " +
                    "where a.userId = :userId order by a.createdDate desc  "
    )
    Page<Notification> getPageByUserId(UUID userId, Pageable pageable);

}
