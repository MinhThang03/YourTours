package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.UserCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserCommand, Long> {

    Optional<UserCommand> findByUserId(UUID userId);

    Boolean existsByEmail(String email);

    Optional<UserCommand> findByEmail(String email);

    @Query(
            nativeQuery = true,
            value = "select a.* " +
                    "from user a, " +
                    "     owner_of_home b " +
                    "where a.userid = b.user_id " +
                    "  and a.userid = :userId " +
                    "limit 1 "
    )
    Optional<UserCommand> findByUserIdAndOwner(UUID userId);

    @Query(
            value = "select a from UserCommand  a order by a.createdDate desc ",
            countQuery = "select a.id from UserCommand  a order by a.createdDate desc "
    )
    Page<UserCommand> getAll(PageRequest pageRequest);
}
