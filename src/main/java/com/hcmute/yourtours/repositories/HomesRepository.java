package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.Homes;
import com.hcmute.yourtours.enums.CommonStatusEnum;
import com.hcmute.yourtours.models.homes.projections.CalculateAverageRateProjection;
import com.hcmute.yourtours.models.homes.projections.CmsHomeInfoProjection;
import com.hcmute.yourtours.models.homes.projections.GetOwnerNameAndHomeNameProjection;
import com.hcmute.yourtours.models.homes.projections.MobileHomeProjection;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
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
                            "         case when :sortBy = 'NEW' then a.created_date end desc,  " +
                            "         case when :sortBy = 'TRENDING' then a.number_of_booking end desc,  " +
                            "         a.created_date desc  ",
                    countQuery = "select a.id  " +
                            "from homes a  " +
                            "         inner join owner_of_home b on a.id = b.home_id  " +
                            "where a.deleted = false  " +
                            "  and (b.user_id = :userId or :userId is null)  " +
                            "  and (a.status = :status or :status is null)  " +
                            "order by case when :sortBy = 'VIEW' then a.view end desc,  " +
                            "         case when :sortBy = 'FAVORITE' then a.favorite end desc,  " +
                            "         case when :sortBy = 'NEW' then a.created_date end desc,  " +
                            "         case when :sortBy = 'TRENDING' then a.number_of_booking end desc,  " +
                            "         a.created_date desc  "
            )
    Page<Homes> findPageWithFilter(@Param("userId") UUID userId,
                                   @Param("sortBy") String sortBy,
                                   @Param("status") String status,
                                   Pageable pageable);


    @Query
            (
                    nativeQuery = true,
                    value = "select a.id                     as id,   " +
                            "       a.name                   as name,   " +
                            "       a.description            as description,   " +
                            "       a.address_detail         as addressDetail,   " +
                            "       a.province_code          as provinceCode,   " +
                            "       a.cost_per_night_default as costPerNightDefault,   " +
                            "       a.refund_policy          as refundPolicy,   " +
                            "       a.status                 as status,   " +
                            "       a.last_modified_date     as lastModifiedDate,   " +
                            "       d.name                   as provinceName,   " +
                            "       c.full_name              as ownerName,   " +
                            "       a.deleted                as deleted,   " +
                            "       c.id                     as ownerId   " +
                            "from homes a,   " +
                            "     owner_of_home b,   " +
                            "     user c,   " +
                            "     province d   " +
                            "where a.id = b.home_id   " +
                            "  and b.user_id = c.id   " +
                            "  and a.province_code = d.code_name   " +
                            "  and (:keyword is null or upper(a.name) like upper(Concat('%', :keyword, '%')))   " +
                            "order by a.created_date desc ",
                    countQuery = "select a.id              " +
                            "from homes a,   " +
                            "     owner_of_home b,   " +
                            "     user c,   " +
                            "     province d   " +
                            "where a.id = b.home_id   " +
                            "  and b.user_id = c.id   " +
                            "  and a.province_code = d.code_name   " +
                            "  and (:keyword is null or upper(a.name) like upper(Concat('%', :keyword, '%')))   " +
                            "order by a.created_date desc "
            )
    Page<CmsHomeInfoProjection> findPageWithFilterWithAdmin(String keyword, Pageable pageable);


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
                    "  and (:provinceCode is null or a.province_code = :provinceCode)  " +
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
                    "  and (:provinceCode is null or a.province_code = :provinceCode)  " +
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
                                      @Param("provinceCode") String provinceCode,
                                      Pageable pageable);


    @Query(
            nativeQuery = true,
            value = "select distinct a.* " +
                    "from homes a, " +
                    "     amenities_of_home b, " +
                    "     province c " +
                    "where a.id = b.home_id " +
                    "  and a.province_code = c.code_name " +
                    "  and (:amenityId is null or b.amenity_id = :amenityId) " +
                    "  and (:province is null or upper(c.name) like upper(Concat('%', :province, '%'))) " +
                    "order by a.created_date desc ",
            countQuery = "select distinct a.id " +
                    "from homes a, " +
                    "     amenities_of_home b, " +
                    "     province c " +
                    "where a.id = b.home_id " +
                    "  and a.province_code = c.code_name " +
                    "  and (:amenityId is null or b.amenity_id = :amenityId) " +
                    "  and (:province is null or upper(c.name) like upper(Concat('%', :province, '%'))) " +
                    "order by a.created_date desc "
    )
    Page<Homes> getPageWithProvinceAndAmenity(@Param("province") String province,
                                              @Param("amenityId") UUID amenityId,
                                              Pageable pageable);


    @Query(
            nativeQuery = true,
            value = "select distinct a.* " +
                    "from homes a, " +
                    "     amenities_of_home b, " +
                    "     province c " +
                    "where a.id = b.home_id " +
                    "  and a.province_code = c.code_name " +
                    "  and (:province is null or upper(c.name) like upper(Concat('%', :province, '%'))) " +
                    "order by a.number_of_booking desc ",
            countQuery = "select distinct a.id " +
                    "from homes a, " +
                    "     amenities_of_home b, " +
                    "     province c " +
                    "where a.id = b.home_id " +
                    "  and a.province_code = c.code_name " +
                    "  and (:province is null or upper(c.name) like upper(Concat('%', :province, '%'))) " +
                    "order by a.number_of_booking desc "
    )
    Page<Homes> getPageRecommend(@Param("province") String province,
                                 Pageable pageable);


    @Query(
            nativeQuery = true,
            value = "select a.*   " +
                    "from homes a,   " +
                    "     province b   " +
                    "where a.province_code = b.code_name   " +
                    "  and (upper(b.name) like upper(Concat('%', :keyword, '%'))   " +
                    "  or upper(a.name) like upper(Concat('%', :keyword, '%'))   " +
                    "  or upper(b.en_name) like upper(Concat('%', :keyword, '%')))   " +
                    "order by a.view desc ",
            countQuery = "select a.id   " +
                    "from homes a,   " +
                    "     province b   " +
                    "where a.province_code = b.code_name   " +
                    "  and (upper(b.name) like upper(Concat('%', :keyword, '%'))   " +
                    "  or upper(a.name) like upper(Concat('%', :keyword, '%'))   " +
                    "  or upper(b.en_name) like upper(Concat('%', :keyword, '%')))   "
    )
    Page<Homes> getPageWithListProvinceCode(@Param("keyword") String keyword,
                                            Pageable pageable);


    @Query(
            nativeQuery = true,
            value = "select a.name      as homeName, " +
                    "       a.cost_per_night_default as baseCost, " +
                    "       b.user_id as ownerId, " +
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


    @Query(
            nativeQuery = true,
            value = "select a.id                  as id,     " +
                    "       a.costPerNightDefault as costPerNightDefault,     " +
                    "       a.name                as name,     " +
                    "       a.view                as view,     " +
                    "       a.numberOfBooking     as numberOfBooking,     " +
                    "       a.favorite            as favorite,     " +
                    "       a.createdDate         as createdDate,     " +
                    "       a.name                as provinceName,     " +
                    "       a.numberOfReview      as numberOfReview,     " +
                    "       a.rates               as rates,     " +
                    "       a.averageRate         as averageRate,     " +
                    "       a.provinceName        as province,     " +
                    "       a.thumbnail           as thumbnail,     " +
                    "       a.isFavorite          as isFavorite     " +
                    "from (select a.id                                                   as id,     " +
                    "             a.cost_per_night_default                               as costPerNightDefault,     " +
                    "             a.name                                                 as name,     " +
                    "             a.view                                                 as view,     " +
                    "             a.number_of_booking                                    as numberOfBooking,     " +
                    "             a.favorite                                             as favorite,     " +
                    "             a.created_date                                         as createdDate,     " +
                    "             a.thumbnail                                            as thumbnail,     " +
                    "             c.name                                                 as provinceName,     " +
                    "             COALESCE(b.numberOfReview, 0)                          as numberOfReview,     " +
                    "             COALESCE(b.rates, 0)                                   as rates,     " +
                    "             (COALESCE(b.rates, 0) / COALESCE(b.numberOfReview, 1)) as averageRate,     " +
                    "             if(d.user_id is null, 'false', 'true')                     as isFavorite     " +
                    "      from (select a.*     " +
                    "            from homes a     " +
                    "            where a.deleted is false     " +
                    "              and (:status is null or a.status = :status)) a     " +
                    "               left join     " +
                    "           (select a.home_id    as homeId,     " +
                    "                   count(a.id)  as numberOfReview,     " +
                    "                   sum(a.rates) as rates     " +
                    "            from book_home a     " +
                    "            where a.rates is not null     " +
                    "            group by a.home_id) b     " +
                    "           on a.id = b.homeId     " +
                    "               inner join province c on a.province_code = c.code_name     " +
                    "               left join (select a.* from item_favorites a where a.user_id = :userId) d on a.id = d.home_id) a     " +
                    "order by case when :sortBy = 'VIEW' then a.view end desc,     " +
                    "         case when :sortBy = 'FAVORITE' then a.favorite end desc,     " +
                    "         case when :sortBy = 'NEW' then a.createdDate end desc,     " +
                    "         case when :sortBy = 'RATE' then (a.averageRate) end desc,     " +
                    "         case when :sortBy = 'TRENDING' then a.numberOfBooking end desc,     " +
                    "         a.createdDate desc ",
            countQuery = "select a.id                  " +
                    "from (select a.id                                                   as id,     " +
                    "             a.cost_per_night_default                               as costPerNightDefault,     " +
                    "             a.name                                                 as name,     " +
                    "             a.view                                                 as view,     " +
                    "             a.number_of_booking                                    as numberOfBooking,     " +
                    "             a.favorite                                             as favorite,     " +
                    "             a.created_date                                         as createdDate,     " +
                    "             a.thumbnail                                            as thumbnail,     " +
                    "             c.name                                                 as provinceName,     " +
                    "             COALESCE(b.numberOfReview, 0)                          as numberOfReview,     " +
                    "             COALESCE(b.rates, 0)                                   as rates,     " +
                    "             (COALESCE(b.rates, 0) / COALESCE(b.numberOfReview, 1)) as averageRate,     " +
                    "             if(d.user_id is null, 'false', 'true')                     as isFavorite     " +
                    "      from (select a.*     " +
                    "            from homes a     " +
                    "            where a.deleted is false     " +
                    "              and (:status is null or a.status = :status)) a     " +
                    "               left join     " +
                    "           (select a.home_id    as homeId,     " +
                    "                   count(a.id)  as numberOfReview,     " +
                    "                   sum(a.rates) as rates     " +
                    "            from book_home a     " +
                    "            where a.rates is not null     " +
                    "            group by a.home_id) b     " +
                    "           on a.id = b.homeId     " +
                    "               inner join province c on a.province_code = c.code_name     " +
                    "               left join (select a.* from item_favorites a where a.user_id = :userId) d on a.id = d.home_id) a     " +
                    "order by case when :sortBy = 'VIEW' then a.view end desc,     " +
                    "         case when :sortBy = 'FAVORITE' then a.favorite end desc,     " +
                    "         case when :sortBy = 'NEW' then a.createdDate end desc,     " +
                    "         case when :sortBy = 'RATE' then (a.averageRate) end desc,     " +
                    "         case when :sortBy = 'TRENDING' then a.numberOfBooking end desc,     " +
                    "         a.createdDate desc "
    )
    Page<MobileHomeProjection> getPageForMobile(@Param("status") String status,
                                                @Param("sortBy") String sortBy,
                                                @Param("userId") UUID userId,
                                                Pageable pageable);


    @Query(
            nativeQuery = true,
            value = "select a.id                  as id,    " +
                    "       a.costPerNightDefault as costPerNightDefault,    " +
                    "       a.name                as name,    " +
                    "       a.view                as view,    " +
                    "       a.numberOfBooking     as numberOfBooking,    " +
                    "       a.favorite            as favorite,    " +
                    "       a.createdDate         as createdDate,    " +
                    "       a.provinceName        as province,    " +
                    "       a.numberOfReview      as numberOfReview,    " +
                    "       a.rates               as rates,    " +
                    "       a.averageRate         as averageRate,    " +
                    "       a.provinceName        as provinceName,    " +
                    "       a.thumbnail           as thumbnail,     " +
                    "       a.isFavorite          as isFavorite    " +
                    "from (select a.id                                                   as id,    " +
                    "             a.cost_per_night_default                               as costPerNightDefault,    " +
                    "             a.name                                                 as name,    " +
                    "             a.view                                                 as view,    " +
                    "             a.number_of_booking                                    as numberOfBooking,    " +
                    "             a.favorite                                             as favorite,    " +
                    "             a.created_date                                         as createdDate,    " +
                    "             a.thumbnail                                            as thumbnail,     " +
                    "             c.name                                                 as provinceName,    " +
                    "             COALESCE(b.numberOfReview, 0)                          as numberOfReview,    " +
                    "             COALESCE(b.rates, 0)                                   as rates,    " +
                    "             (COALESCE(b.rates, 0) / COALESCE(b.numberOfReview, 1)) as averageRate,    " +
                    "             if(d.user_id is null, 'false', 'true')                     as isFavorite    " +
                    "      from (select a.*    " +
                    "            from homes a    " +
                    "            where a.deleted is false    " +
                    "              and (:status is null or a.status = :status)) a    " +
                    "               left join    " +
                    "           (select a.home_id    as homeId,    " +
                    "                   count(a.id)  as numberOfReview,    " +
                    "                   sum(a.rates) as rates    " +
                    "            from book_home a    " +
                    "            where a.rates is not null    " +
                    "            group by a.home_id) b    " +
                    "           on a.id = b.homeId    " +
                    "               inner join province c on a.province_code = c.code_name    " +
                    "               inner join (select a.* from item_favorites a where a.user_id = :userId order by a.created_date) d on a.id = d.home_id) a ",
            countQuery = "select a.id   " +
                    "from (select a.*   " +
                    "      from homes a   " +
                    "      where a.deleted is false   " +
                    "        and (:status is null or a.status = :status)) a   " +
                    "         inner join province c on a.province_code = c.code_name   " +
                    "         inner join (select a.* from item_favorites a where a.user_id = :userId order by a.created_date) d   " +
                    "                    on a.id = d.home_id "
    )
    Page<MobileHomeProjection> getPageFavoriteMobile(
            @Param("status") String status,
            @Param("userId") UUID userId,
            Pageable pageable);


    @Query(
            nativeQuery = true,
            value = "select a.id                  as id,    " +
                    "       a.costPerNightDefault as costPerNightDefault,    " +
                    "       a.name                as name,    " +
                    "       a.view                as view,    " +
                    "       a.numberOfBooking     as numberOfBooking,    " +
                    "       a.favorite            as favorite,    " +
                    "       a.createdDate         as createdDate,    " +
                    "       a.name                as provinceName,    " +
                    "       a.numberOfReview      as numberOfReview,    " +
                    "       a.rates               as rates,    " +
                    "       a.averageRate         as averageRate,    " +
                    "       a.provinceName        as province,    " +
                    "       a.thumbnail           as thumbnail,     " +
                    "       a.isFavorite          as isFavorite    " +
                    "from (select a.id                                                   as id,    " +
                    "             a.cost_per_night_default                               as costPerNightDefault,    " +
                    "             a.name                                                 as name,    " +
                    "             a.view                                                 as view,    " +
                    "             a.number_of_booking                                    as numberOfBooking,    " +
                    "             a.favorite                                             as favorite,    " +
                    "             a.created_date                                         as createdDate,    " +
                    "             a.thumbnail                                            as thumbnail,     " +
                    "             c.name                                                 as provinceName,    " +
                    "             COALESCE(b.numberOfReview, 0)                          as numberOfReview,    " +
                    "             COALESCE(b.rates, 0)                                   as rates,    " +
                    "             (COALESCE(b.rates, 0) / COALESCE(b.numberOfReview, 1)) as averageRate,    " +
                    "             if(d.user_id is null, 'false', 'true')                     as isFavorite    " +
                    "      from (select distinct a.*    " +
                    "            from homes a,    " +
                    "                 amenities_of_home b    " +
                    "            where a.deleted is false    " +
                    "               and (a.id = b.home_id)    " +
                    "              and (:status is null or a.status = :status)    " +
                    "              and (:amenityId is null or b.amenity_id = :amenityId)) a    " +
                    "               left join    " +
                    "           (select a.home_id    as homeId,    " +
                    "                   count(a.id)  as numberOfReview,    " +
                    "                   sum(a.rates) as rates    " +
                    "            from book_home a    " +
                    "            where a.rates is not null    " +
                    "            group by a.home_id) b    " +
                    "           on a.id = b.homeId    " +
                    "               inner join province c on a.province_code = c.code_name and    " +
                    "                                        ((:province is null or upper(c.name) like upper(Concat('%', :province, '%'))) or (:province is null or upper(c.en_name) like upper(Concat('%', :province, '%'))))    " +
                    "               left join (select a.* from item_favorites a where a.user_id = :userId order by a.created_date) d    " +
                    "                          on a.id = d.home_id) a    " +
                    "order by a.createdDate desc ",
            countQuery = "select a.id    " +
                    "from (select distinct a.*    " +
                    "      from homes a,    " +
                    "           amenities_of_home b    " +
                    "      where a.deleted is false    " +
                    "        and (a.id = b.home_id)    " +
                    "        and (:status is null or a.status = :status)    " +
                    "        and (:amenityId is null or b.amenity_id = :amenityId)) a    " +
                    "         inner join province c on a.province_code = c.code_name and    " +
                    "                                  ((:province is null or upper(c.name) like upper(Concat('%', :province, '%')))  or (:province is null or upper(c.en_name) like upper(Concat('%', :province, '%'))))  " +
                    "         left join (select a.* from item_favorites a where a.user_id = :userId order by a.created_date) d    " +
                    "                   on a.id = d.home_id "
    )
    Page<MobileHomeProjection> getMobilePageWithProvinceAndAmenity(@Param("status") String status,
                                                                   @Param("province") String province,
                                                                   @Param("amenityId") UUID amenityId,
                                                                   @Param("userId") UUID userId,
                                                                   Pageable pageable);


    @Query(
            nativeQuery = true,
            value = "   " +
                    "select a.homeId                                               as id,   " +
                    "       COALESCE(a.numberOfReview, 0)                          as numberOfReview,   " +
                    "       COALESCE(a.rates, 0)                                   as rates,   " +
                    "       (COALESCE(a.rates, 0) / COALESCE(a.numberOfReview, 1)) as averageRate   " +
                    "from (select a.home_id    as homeId,   " +
                    "             count(a.id)  as numberOfReview,   " +
                    "             sum(a.rates) as rates   " +
                    "      from book_home a   " +
                    "      where a.rates is not null   " +
                    "        and a.home_id = :homeId) a "
    )
    CalculateAverageRateProjection calculateAverageRate(@Param("homeId") UUID homeId);


    @Query
            (
                    value =
                            "select a from Homes a inner join OwnerOfHome b on a.id = b.homeId " +
                                    "where a.deleted is false " +
                                    "and (:keyword is null or upper(a.name) like Concat('%', upper(:keyword) , '%') ) " +
                                    "and (coalesce(:statusList, null ) is null or a.status in :statusList)" +
                                    "and (:userId is null or b.userId = :userId) " +
                                    "order by a.createdDate desc ",
                    countQuery = "select a.id from Homes a inner join OwnerOfHome b on a.id = b.homeId " +
                            "where a.deleted is false " +
                            "and (:keyword is null or upper(a.name) like Concat('%', upper(:keyword) , '%') ) " +
                            "and (coalesce(:statusList, null ) is null or a.status in :statusList)" +
                            "and (:userId is null or b.userId = :userId) " +
                            "order by a.createdDate desc "
            )
    Page<Homes> getCmsPageHome(
            @Param("userId") UUID userId,
            @Param("statusList") List<CommonStatusEnum> statusList,
            @Param("keyword") String keyword,
            Pageable pageable);
}
