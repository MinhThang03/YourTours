package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.UserEvaluate;
import com.hcmute.yourtours.models.user_evaluate.projection.UserEvaluateProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserEvaluateRepository extends JpaRepository<UserEvaluate, UUID> {

    Optional<UserEvaluate> findByUserIdAndHomeId(UUID userId, UUID homeId);

    List<UserEvaluate> findAllByHomeIdAndPointIsNotNull(UUID homeId);

    @Query(
            nativeQuery = true,
            value = "select a.*  " +
                    "from user_evaluate a  " +
                    "where case  " +
                    "          when :typeFilter = 'RATE' then a.point is not null  " +
                    "          when :typeFilter = 'COMMENT' then a.comment is not null  " +
                    "          else '1' = '1' end  " +
                    "  and (:homeId is null or :homeId = a.home_id)",
            countQuery = "select a.id  " +
                    "from user_evaluate a  " +
                    "where case  " +
                    "          when :typeFilter = 'RATE' then a.point is not null  " +
                    "          when :typeFilter = 'COMMENT' then a.comment is not null  " +
                    "          else '1' = '1' end  " +
                    "  and (:homeId is null or :homeId = a.home_id) "
    )
    Page<UserEvaluate> findPageWithEvaluateFilter(@Param("typeFilter") String typeFilter,
                                                  @Param("homeId") UUID homeId,
                                                  Pageable pageable);


    @Query(
            nativeQuery = true,
            value = "select a.comment            as comment, " +
                    "       a.point              as point, " +
                    "       a.user_id            as userId, " +
                    "       a.home_id            as homeId, " +
                    "       b.full_name          as userFullName, " +
                    "       b.avatar             as userAvatar, " +
                    "       a.last_modified_date as lastModifiedDate " +
                    "from ((select a.* " +
                    "       from user_evaluate a " +
                    "       where a.user_id = :userId " +
                    "         and a.home_id = :homeId) " +
                    "      union " +
                    "      (select b.* " +
                    "       from user_evaluate b " +
                    "       where b.user_id != :userId " +
                    "         and b.home_id = :homeId " +
                    "       order by b.created_date desc)) a " +
                    "         inner join user b on a.user_id = b.id "
    )
    Page<UserEvaluateProjection> findPageWithUserId(@Param("userId") UUID userId,
                                                    @Param("homeId") UUID homeId,
                                                    Pageable pageable);


    @Query(
            nativeQuery = true,
            value = "select a.comment            as comment,  " +
                    "       a.point              as point,  " +
                    "       a.user_id            as userId,  " +
                    "       a.home_id            as homeId,  " +
                    "       b.full_name          as userFullName,  " +
                    "       b.avatar             as userAvatar,  " +
                    "       a.last_modified_date as lastModifiedDate  " +
                    "from (select b.*  " +
                    "      from user_evaluate b  " +
                    "      where b.home_id = :homeId  " +
                    "      order by b.created_date desc) a  " +
                    "         inner join user b on a.user_id = b.id "
    )
    Page<UserEvaluateProjection> findPageWithoutUserId(@Param("homeId") UUID homeId,
                                                       Pageable pageable);
}
