package com.hcmute.yourtours.libs.cache;

import org.springframework.data.redis.cache.*;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * TimeRedisCacheManager override {@link TimeRedisCacheManager#createRedisCache(String, RedisCacheConfiguration)}
 * <p>
 * create TimeRedisCache
 */
public class TimeRedisCacheManager extends RedisCacheManager {

    private final RedisCacheWriter cacheWriter;
    private final RedisCacheConfiguration defaultCacheConfig;
    private final String serviceName;

    public TimeRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, String serviceName) {
        super(cacheWriter, defaultCacheConfiguration);
        this.cacheWriter = cacheWriter;
        this.defaultCacheConfig = defaultCacheConfiguration;
        this.serviceName = serviceName;
    }

    public TimeRedisCacheManager(
            RedisCacheWriter cacheWriter,
            RedisCacheConfiguration defaultCacheConfiguration,
            boolean allowInFlightCacheCreation,
            String serviceName
    ) {

        super(cacheWriter, defaultCacheConfiguration, allowInFlightCacheCreation);
        this.cacheWriter = cacheWriter;
        this.defaultCacheConfig = defaultCacheConfiguration;
        this.serviceName = serviceName;
    }


    public static TimeRedisCacheManagerBuilder timeBuilder(RedisConnectionFactory connectionFactory) {

        Assert.notNull(connectionFactory, "ConnectionFactory must not be null!");

        return TimeRedisCacheManagerBuilder.fromConnectionFactory(connectionFactory);
    }

    /**
     * Return {@link TimeRedisCache} extends of {@link RedisCache}
     *
     * @param name
     * @param cacheConfig
     * @return new instant of {@link TimeRedisCache}
     */
    @Override
    protected RedisCache createRedisCache(String name, @Nullable RedisCacheConfiguration cacheConfig) {
        return new TimeRedisCache(name, cacheWriter, cacheConfig != null ? cacheConfig : defaultCacheConfig, serviceName);
    }

    public static class TimeRedisCacheManagerBuilder {

        boolean allowInFlightCacheCreation = true;
        private @Nullable
        RedisCacheWriter cacheWriter;
        private CacheStatisticsCollector statisticsCollector = CacheStatisticsCollector.none();
        private RedisCacheConfiguration defaultCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        private boolean enableTransactions;
        private String serviceName;

        private TimeRedisCacheManagerBuilder() {
        }

        private TimeRedisCacheManagerBuilder(RedisCacheWriter cacheWriter) {
            this.cacheWriter = cacheWriter;
        }

        public static TimeRedisCacheManagerBuilder fromConnectionFactory(RedisConnectionFactory connectionFactory) {

            Assert.notNull(connectionFactory, "ConnectionFactory must not be null!");

            return new TimeRedisCacheManagerBuilder(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory));
        }

        public static TimeRedisCacheManagerBuilder fromCacheWriter(RedisCacheWriter cacheWriter) {

            Assert.notNull(cacheWriter, "CacheWriter must not be null!");

            return new TimeRedisCacheManagerBuilder(cacheWriter);
        }

        public TimeRedisCacheManagerBuilder cacheDefaults(RedisCacheConfiguration defaultCacheConfiguration) {

            Assert.notNull(defaultCacheConfiguration, "DefaultCacheConfiguration must not be null!");

            this.defaultCacheConfiguration = defaultCacheConfiguration;

            return this;
        }

        public TimeRedisCacheManagerBuilder cacheWriter(RedisCacheWriter cacheWriter) {

            Assert.notNull(cacheWriter, "CacheWriter must not be null!");

            this.cacheWriter = cacheWriter;

            return this;
        }

        public TimeRedisCacheManagerBuilder transactionAware() {

            this.enableTransactions = true;

            return this;
        }

        public TimeRedisCacheManagerBuilder disableCreateOnMissingCache() {

            this.allowInFlightCacheCreation = false;
            return this;
        }


        public TimeRedisCacheManagerBuilder enableStatistics() {

            this.statisticsCollector = CacheStatisticsCollector.create();
            return this;
        }

        public TimeRedisCacheManagerBuilder serviceName(String serviceName) {

            this.serviceName = serviceName;
            return this;
        }


        public TimeRedisCacheManager build() {

            Assert.state(cacheWriter != null,
                    "CacheWriter must not be null! You can provide one via 'RedisCacheManagerBuilder#cacheWriter(RedisCacheWriter)'.");

            RedisCacheWriter theCacheWriter = cacheWriter;

            if (!statisticsCollector.equals(CacheStatisticsCollector.none())) {
                theCacheWriter = cacheWriter.withStatisticsCollector(statisticsCollector);
            }

            TimeRedisCacheManager cm = new TimeRedisCacheManager(
                    theCacheWriter,
                    defaultCacheConfiguration,
                    allowInFlightCacheCreation,
                    serviceName
            );

            cm.setTransactionAware(enableTransactions);

            return cm;
        }
    }

}
