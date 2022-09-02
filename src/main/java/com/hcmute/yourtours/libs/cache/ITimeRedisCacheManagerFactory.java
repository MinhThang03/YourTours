package com.hcmute.yourtours.libs.cache;

/**
 * Factory create {@link TimeRedisCacheManager}
 */
public interface ITimeRedisCacheManagerFactory {
    /**
     * Create TimeRedisCacheManager
     *
     * @return instant of TimeRedisCacheManager
     */
    TimeRedisCacheManager cacheManager();
}
