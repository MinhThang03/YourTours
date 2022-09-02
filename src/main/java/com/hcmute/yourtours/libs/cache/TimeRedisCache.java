package com.hcmute.yourtours.libs.cache;

import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;

import java.time.Duration;

/**
 * Additional of {@link RedisCache}
 * <p>
 * Using {@link TimeRedisCache} to create {@link TimeRedisCache}
 */
public class TimeRedisCache extends RedisCache implements ITimeRedisCache {
    private final String name;
    private final RedisCacheWriter cacheWriter;
    private final String serviceName;
    private final String SEPARATOR = "::";

    protected TimeRedisCache(String name, RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig, String serviceName) {
        super(name, cacheWriter, cacheConfig);
        this.name = name;
        this.cacheWriter = cacheWriter;
        this.serviceName = serviceName;
    }

    @Override
    public void put(Object key, Object value, Duration ttl) {
        Object cacheValue = preProcessCacheValue(value);

        if (!isAllowNullValues() && cacheValue == null) {

            throw new IllegalArgumentException(String.format(
                    "Cache '%s' does not allow 'null' values. Avoid storing null via '@Cacheable(unless=\"#result == null\")' or configure RedisCache to allow 'null' via RedisCacheConfiguration.",
                    name));
        }
        cacheWriter.put(name, createAndConvertCacheKey(key), serializeCacheValue(cacheValue), ttl);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value, Duration ttl) {
        Object cacheValue = preProcessCacheValue(value);

        if (!isAllowNullValues() && cacheValue == null) {
            return get(key);
        }

        byte[] result = cacheWriter.putIfAbsent(name, createAndConvertCacheKey(key), serializeCacheValue(cacheValue),
                ttl);

        if (result == null) {
            return null;
        }

        return new SimpleValueWrapper(fromStoreValue(deserializeCacheValue(result)));
    }

    private byte[] createAndConvertCacheKey(Object key) {
        return serializeCacheKey(createCacheKey(key));
    }

    @Override
    protected String createCacheKey(Object key) {
        String convertedKey = super.createCacheKey(key);
        return prefixService(convertedKey);
    }

    private String prefixService(String convertedKey) {
        return getServiceName() + SEPARATOR + convertedKey;
    }

    private String getServiceName() {
        return this.serviceName;
    }

}
