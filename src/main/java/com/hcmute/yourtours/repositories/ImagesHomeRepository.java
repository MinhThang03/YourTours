package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.ImagesHome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImagesHomeRepository extends JpaRepository<ImagesHome, UUID> {

    @Modifying
    void deleteAllByHomeId(UUID homeId);

    List<ImagesHome> findAllByHomeId(UUID homeId);
}
