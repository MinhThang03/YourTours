package com.hcmute.yourtours.libs.cache;

import org.springframework.cache.Cache;
import org.springframework.lang.Nullable;

import java.time.Duration;

/**
 * Extend {@link Cache} interface defines two put method have expiration time
 */
public interface ITimeRedisCache extends Cache {
    /**
     * Associate the specified value with the specified key in this cache.
     * <p>If the cache previously contained a mapping for this key, the old
     * value is replaced by the specified value.
     * <p>Actual registration may be performed in an asynchronous or deferred
     * fashion, with subsequent lookups possibly not seeing the entry yet.
     * This may for example be the case with transactional cache decorators.
     * Use {@link #putIfAbsent} for guaranteed immediate registration.
     *
     * @param key   the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @param ttl   Optional expiration time. Can be null
     * @see #putIfAbsent(Object, Object)
     */
    void put(Object key, @Nullable Object value, Duration ttl);

    /**
     * Atomically associate the specified value with the specified key in this cache
     * if it is not set already.
     *
     * @param key   the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @param ttl   Optional expiration time. Can be {@literal null}.
     * @return {@literal null} if the value has been written, the value stored for the key if it already exists.
     */
    ValueWrapper putIfAbsent(Object key, @Nullable Object value, Duration ttl);
}
