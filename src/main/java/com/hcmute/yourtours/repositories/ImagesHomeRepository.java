package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.ImagesHomeCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ImagesHomeRepository extends JpaRepository<ImagesHomeCommand, Long> {
    Optional<ImagesHomeCommand> findByImageId(UUID imageId);
}
