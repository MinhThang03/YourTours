package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.ImagesRoomHome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ImagesRoomHomeRepository extends JpaRepository<ImagesRoomHome, Long> {
    Optional<ImagesRoomHome> findByImageId(UUID imageId);
}
