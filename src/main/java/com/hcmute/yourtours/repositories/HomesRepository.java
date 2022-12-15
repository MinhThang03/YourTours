package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.HomesCommand;
import com.hcmute.yourtours.models.province.ProvinceProjection;
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
public interface HomesRepository extends JpaRepository<HomesCommand, Long> {
    Optional<HomesCommand> findByHomeId(UUID homeID);

    @Query
            (
                    nativeQuery = true,
                    value = "select a.*  " +
                            "from homes a  " +
                            "         inner join owner_of_home b on a.home_id = b.home_id  " +
                            "where a.deleted = false  " +
                            "  and (b.user_id = :userId or :userId is null)  " +
                            "  and (a.status = :status or :status is null)  " +
                            "order by case when :sortBy = 'VIEW' then a.view end desc,  " +
                            "         case when :sortBy = 'FAVORITE' then a.favorite end desc,  " +
                            "         a.created_date desc  ",
                    countQuery = "select a.id  " +
                            "from homes a  " +
                            "         inner join owner_of_home b on a.home_id = b.home_id  " +
                            "where a.deleted = false  " +
                            "  and (b.user_id = :userId or :userId is null)  " +
                            "  and (a.status = :status or :status is null)  " +
                            "order by case when :sortBy = 'VIEW' then a.view end desc,  " +
                            "         case when :sortBy = 'FAVORITE' then a.favorite end desc,  " +
                            "         a.created_date desc  "
            )
    Page<HomesCommand> findPageWithFilter(@Param("userId") UUID userId,
                                          @Param("sortBy") String sortBy,
                                          @Param("status") String status,
                                          PageRequest pageRequest);


    @Query
            (
                    nativeQuery = true,
                    value = "select count(a.id) as numberBooking, a.province_code as provinceCode " +
                            "from homes a, " +
                            "     book_home b " +
                            "where a.home_id = b.home_id " +
                            "group by a.province_code " +
                            "order by count(a.id) desc ",
                    countQuery = "select count(a.id) as numberBooking, a.province_code as provinceCode " +
                            "from homes a, " +
                            "     book_home b " +
                            "where a.home_id = b.home_id " +
                            "group by a.province_code " +
                            "order by count(a.id) desc "
            )
    Page<ProvinceProjection> getProvinceWithFilter(Pageable pageable);


    @Query(
            nativeQuery = true,
            value = "select a.*  " +
                    "from homes a,  " +
                    "     item_favorites b  " +
                    "where a.home_id = b.home_id  " +
                    "  and b.user_id = :userId  " +
                    "  and a.status = :status ",
            countQuery = "select a.id  " +
                    "from homes a,  " +
                    "     item_favorites b  " +
                    "where a.home_id = b.home_id  " +
                    "  and b.user_id = :userId  " +
                    "  and a.status = :status "
    )
    Page<HomesCommand> getFavoritesListByUserId(@Param("userId") UUID userId,
                                                @Param("status") String status,
                                                Pageable pageable);


    @Query(
            nativeQuery = true,
            value = "select a.*  " +
                    "from homes a,  " +
                    "     item_favorites b  " +
                    "where a.home_id = :homeId  " +
                    "  and b.user_id = :userId  " +
                    "  and a.home_id = b.home_id  " +
                    "limit 1 "
    )
    Optional<HomesCommand> findIsFavoriteByHomeIdAndUserId(@Param("homeId") UUID homeId,
                                                           @Param("userId") UUID userId);


    @Query(
            nativeQuery = true,
            value = "select distinct a.*  " +
                    "from homes a  " +
                    "         left join  " +
                    "     amenities_of_home b on a.home_id = b.home_id  " +
                    "         left join (select count(a.id) as numberOfBed,  " +
                    "                           b.home_id  " +
                    "                    from beds_of_home a,  " +
                    "                         rooms_of_home b  " +
                    "                    where a.room_of_home_id = b.room_of_home_id  " +
                    "                    group by b.home_id) c on b.home_id = c.home_id  " +
                    "         left join (select count(a.id) as numberOfBedRoom,  " +
                    "                           a.home_id  " +
                    "                    from rooms_of_home a  " +
                    "                    where a.room_category_id = :bedRoomId  " +
                    "                    group by a.home_id) d on a.home_id = d.home_id  " +
                    "         left join (select count(a.id) as numberOfBathRoom,  " +
                    "                           a.home_id  " +
                    "                    from rooms_of_home a  " +
                    "                    where a.room_category_id = :bathRoomId  " +
                    "                    group by a.home_id) e on e.home_id = a.home_id  " +
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
                    "     amenities_of_home b on a.home_id = b.home_id  " +
                    "         left join (select count(a.id) as numberOfBed,  " +
                    "                           b.home_id  " +
                    "                    from beds_of_home a,  " +
                    "                         rooms_of_home b  " +
                    "                    where a.room_of_home_id = b.room_of_home_id  " +
                    "                    group by b.home_id) c on b.home_id = c.home_id  " +
                    "         left join (select count(a.id) as numberOfBedRoom,  " +
                    "                           a.home_id  " +
                    "                    from rooms_of_home a  " +
                    "                    where a.room_category_id = :bedRoomId  " +
                    "                    group by a.home_id) d on a.home_id = d.home_id  " +
                    "         left join (select count(a.id) as numberOfBathRoom,  " +
                    "                           a.home_id  " +
                    "                    from rooms_of_home a  " +
                    "                    where a.room_category_id = :bathRoomId  " +
                    "                    group by a.home_id) e on e.home_id = a.home_id  " +
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
    Page<HomesCommand> getPageWithFullFilter(@Param("amenityId") UUID amenityId,
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
            value = "select a.*  " +
                    "from homes a  " +
                    "where a.province_code in :listProvinceCode  " +
                    "  and a.status = 'ACTIVE'  " +
                    "order by a.view desc ",
            countQuery = "select a.id  " +
                    "from homes a  " +
                    "where a.province_code in :listProvinceCode  " +
                    "  and a.status = 'ACTIVE'  " +
                    "order by a.view desc  "
    )
    Page<HomesCommand> getPageWithListProvinceCode(@Param("listProvinceCode") List<Integer> listProvinceCode,
                                                   Pageable pageable);
}
