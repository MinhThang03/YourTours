package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.ImagesRoomHomeCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ImagesRoomHomeRepository extends JpaRepository<ImagesRoomHomeCommand, Long> {
    Optional<ImagesRoomHomeCommand> findByImageId(UUID imageId);
}
