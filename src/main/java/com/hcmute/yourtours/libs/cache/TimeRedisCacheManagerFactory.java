package com.hcmute.yourtours.libs.cache;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

/**
 * Default implement {@link ITimeRedisCacheManagerFactory}
 */
public class TimeRedisCacheManagerFactory implements ITimeRedisCacheManagerFactory {
    private final CacheProperties cacheProperties;
    private final ObjectProvider<RedisCacheConfiguration> redisCacheConfiguration;
    private final RedisConnectionFactory redisConnectionFactory;
    private final ResourceLoader resourceLoader;
    private final Environment environment;

    public TimeRedisCacheManagerFactory(
            CacheProperties cacheProperties,
            ObjectProvider<RedisCacheConfiguration> redisCacheConfiguration,
            RedisConnectionFactory redisConnectionFactory,
            ResourceLoader resourceLoader,
            Environment environment
    ) {
        this.cacheProperties = cacheProperties;
        this.redisCacheConfiguration = redisCacheConfiguration;
        this.redisConnectionFactory = redisConnectionFactory;
        this.resourceLoader = resourceLoader;
        this.environment = environment;
    }

    @Override
    public TimeRedisCacheManager cacheManager() {
        TimeRedisCacheManager.TimeRedisCacheManagerBuilder builder = TimeRedisCacheManager.timeBuilder(redisConnectionFactory).cacheDefaults(
                determineConfiguration(cacheProperties, redisCacheConfiguration, resourceLoader.getClassLoader()));
        if (cacheProperties.getRedis().isEnableStatistics()) {
            builder.enableStatistics();
        }
        builder.serviceName(environment.getProperty("spring.application.name"));
        return builder.build();
    }

    private RedisCacheConfiguration determineConfiguration(
            CacheProperties cacheProperties,
            ObjectProvider<RedisCacheConfiguration> redisCacheConfiguration,
            ClassLoader classLoader) {
        return redisCacheConfiguration.getIfAvailable(() -> createConfiguration(cacheProperties, classLoader));
    }

    private RedisCacheConfiguration createConfiguration(
            CacheProperties cacheProperties, ClassLoader classLoader) {
        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        RedisCacheConfiguration config = RedisCacheConfiguration
                .defaultCacheConfig();
        config = config.serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer(classLoader)));
        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        if (redisProperties.getKeyPrefix() != null) {
            config = config.prefixCacheNameWith(redisProperties.getKeyPrefix());
        }
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }
        return config;
    }
}
