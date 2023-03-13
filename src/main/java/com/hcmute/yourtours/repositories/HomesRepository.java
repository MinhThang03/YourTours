package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.Homes;
import com.hcmute.yourtours.models.homes.projections.GetOwnerNameAndHomeNameProjection;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HomesRepository extends JpaRepository<Homes, UUID> {

    @Query
            (
                    nativeQuery = true,
                    value = "select a.*  " +
                            "from homes a  " +
                            "         inner join owner_of_home b on a.id = b.home_id  " +
                            "where a.deleted = false  " +
                            "  and (b.user_id = :userId or :userId is null)  " +
                            "  and (a.status = :status or :status is null)  " +
                            "order by case when :sortBy = 'VIEW' then a.view end desc,  " +
                            "         case when :sortBy = 'FAVORITE' then a.favorite end desc,  " +
                            "         a.created_date desc  ",
                    countQuery = "select a.id  " +
                            "from homes a  " +
                            "         inner join owner_of_home b on a.id = b.home_id  " +
                            "where a.deleted = false  " +
                            "  and (b.user_id = :userId or :userId is null)  " +
                            "  and (a.status = :status or :status is null)  " +
                            "order by case when :sortBy = 'VIEW' then a.view end desc,  " +
                            "         case when :sortBy = 'FAVORITE' then a.favorite end desc,  " +
                            "         a.created_date desc  "
            )
    Page<Homes> findPageWithFilter(@Param("userId") UUID userId,
                                   @Param("sortBy") String sortBy,
                                   @Param("status") String status,
                                   PageRequest pageRequest);


    @Query(
            nativeQuery = true,
            value = "select a.*  " +
                    "from homes a,  " +
                    "     item_favorites b  " +
                    "where a.id = b.home_id  " +
                    "  and b.user_id = :userId  " +
                    "  and a.status = :status ",
            countQuery = "select a.id  " +
                    "from homes a,  " +
                    "     item_favorites b  " +
                    "where a.id = b.home_id  " +
                    "  and b.user_id = :userId  " +
                    "  and a.status = :status "
    )
    Page<Homes> getFavoritesListByUserId(@Param("userId") UUID userId,
                                         @Param("status") String status,
                                         Pageable pageable);


    @Query(
            nativeQuery = true,
            value = "select a.*  " +
                    "from homes a,  " +
                    "     item_favorites b  " +
                    "where a.id = :homeId  " +
                    "  and b.user_id = :userId  " +
                    "  and a.id = b.home_id  " +
                    "limit 1 "
    )
    Optional<Homes> findIsFavoriteByHomeIdAndUserId(@Param("homeId") UUID homeId,
                                                    @Param("userId") UUID userId);


    @Query(
            nativeQuery = true,
            value = "select distinct a.*  " +
                    "from homes a  " +
                    "         left join  " +
                    "     amenities_of_home b on a.id = b.home_id  " +
                    "         left join (select count(a.id) as numberOfBed,  " +
                    "                           b.home_id  " +
                    "                    from beds_of_home a,  " +
                    "                         rooms_of_home b  " +
                    "                    where a.room_of_home_id = b.id  " +
                    "                    group by b.home_id) c on b.home_id = c.home_id  " +
                    "         left join (select count(a.id) as numberOfBedRoom,  " +
                    "                           a.home_id  " +
                    "                    from rooms_of_home a  " +
                    "                    where a.room_category_id = :bedRoomId  " +
                    "                    group by a.home_id) d on a.id = d.home_id  " +
                    "         left join (select count(a.id) as numberOfBathRoom,  " +
                    "                           a.home_id  " +
                    "                    from rooms_of_home a  " +
                    "                    where a.room_category_id = :bathRoomId  " +
                    "                    group by a.home_id) e on e.home_id = a.id  " +
                    "where (:amenityId is null or b.amenity_id = :amenityId)  " +
                    "  and (:priceFrom is null or :priceTo is null or  " +
                    "       (a.cost_per_night_default >= :priceFrom and a.cost_per_night_default <= :priceTo))  " +
                    "  and (:numberOfBed is null or c.numberOfBed = :numberOfBed)  " +
                    "  and (:numberOfBedRoom is null or d.numberOfBedRoom = :numberOfBedRoom)  " +
                    "  and (:numberOfBathRoom is null or e.numberOfBathRoom = :numberOfBathRoom)  " +
                    "  and IF(:size > 0, b.amenity_id in :amenities, true)  " +
                    "  and a.status = 'ACTIVE'  " +
                    "order by a.created_date desc ",
            countQuery = "select distinct a.id  " +
                    "from homes a  " +
                    "         left join  " +
                    "     amenities_of_home b on a.id = b.home_id  " +
                    "         left join (select count(a.id) as numberOfBed,  " +
                    "                           b.home_id  " +
                    "                    from beds_of_home a,  " +
                    "                         rooms_of_home b  " +
                    "                    where a.room_of_home_id = b.id  " +
                    "                    group by b.home_id) c on b.home_id = c.home_id  " +
                    "         left join (select count(a.id) as numberOfBedRoom,  " +
                    "                           a.home_id  " +
                    "                    from rooms_of_home a  " +
                    "                    where a.room_category_id = :bedRoomId  " +
                    "                    group by a.home_id) d on a.id = d.home_id  " +
                    "         left join (select count(a.id) as numberOfBathRoom,  " +
                    "                           a.home_id  " +
                    "                    from rooms_of_home a  " +
                    "                    where a.room_category_id = :bathRoomId  " +
                    "                    group by a.home_id) e on e.home_id = a.id  " +
                    "where (:amenityId is null or b.amenity_id = :amenityId)  " +
                    "  and (:priceFrom is null or :priceTo is null or  " +
                    "       (a.cost_per_night_default >= :priceFrom and a.cost_per_night_default <= :priceTo))  " +
                    "  and (:numberOfBed is null or c.numberOfBed = :numberOfBed)  " +
                    "  and (:numberOfBedRoom is null or d.numberOfBedRoom = :numberOfBedRoom)  " +
                    "  and (:numberOfBathRoom is null or e.numberOfBathRoom = :numberOfBathRoom)  " +
                    "  and IF(:size > 0, b.amenity_id in :amenities, true)  " +
                    "  and a.status = 'ACTIVE'  " +
                    "order by a.created_date desc "
    )
    Page<Homes> getPageWithFullFilter(@Param("amenityId") UUID amenityId,
                                      @Param("priceFrom") Double priceFrom,
                                      @Param("priceTo") Double priceTo,
                                      @Param("numberOfBed") Integer numberOfBed,
                                      @Param("numberOfBedRoom") Integer numberOfBedRoom,
                                      @Param("numberOfBathRoom") Integer numberOfBathRoom,
                                      @Param("amenities") List<UUID> amenities,
                                      @Param("size") Integer size,
                                      @Param("bedRoomId") UUID bedRoomId,
                                      @Param("bathRoomId") UUID bathRoomId,
                                      Pageable pageable);


    @Query(
            nativeQuery = true,
            value = "select a.*   " +
                    "from homes a,   " +
                    "     province b   " +
                    "where a.province_code = b.code_name   " +
                    "  and upper(b.name) like upper(Concat('%', :keyword, '%'))   " +
                    "order by a.view desc ",
            countQuery = "select a.id   " +
                    "from homes a,   " +
                    "     province b   " +
                    "where a.province_code = b.code_name   " +
                    "  and upper(b.name) like upper(Concat('%', :keyword, '%'))   "
    )
    Page<Homes> getPageWithListProvinceCode(@Param("keyword") String keyword,
                                            Pageable pageable);


    @Query(
            nativeQuery = true,
            value = "select a.name      as homeName, " +
                    "       a.cost_per_night_default as baseCost, " +
                    "       c.full_name as ownerName " +
                    "from homes a, " +
                    "     owner_of_home b, " +
                    "     user c " +
                    "where a.id = b.home_id " +
                    "  and b.user_id = c.id " +
                    "  and b.is_main_owner is true " +
                    "  and a.id = :homeId "
    )
    GetOwnerNameAndHomeNameProjection getOwnerNameAndHomeName(UUID homeId);
}
