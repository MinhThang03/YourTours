package com.hcmute.yourtours.libs.factory;

import org.springframework.lang.NonNull;

import java.time.Duration;

public interface CacheFactoryConfig<T> {
    default boolean cache() {
        return true;
    }

    @NonNull
    Class<T> objectClass();

    default String cacheName() {
        return objectClass().getSimpleName();
    }

    default String cacheNameCollection() {
        return "List".concat(objectClass().getSimpleName());
    }

    default Duration singleTtl() {
        return Duration.ofSeconds(50);
    }

    default Duration collectionTtl() {
        return Duration.ofSeconds(5);
    }

    default boolean create() {
        return true;
    }

    default boolean update() {
        return true;
    }


    default boolean getList() {
        return true;
    }

    default boolean getListFilter() {
        return true;
    }


    default boolean getDetail() {
        return true;
    }

    default boolean getPaging() {
        return true;
    }


    default boolean existByFilter() {
        return true;
    }

    default boolean existByDetail() {
        return true;
    }

}
