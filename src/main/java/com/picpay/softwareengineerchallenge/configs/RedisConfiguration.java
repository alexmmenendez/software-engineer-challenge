package com.picpay.softwareengineerchallenge.configs;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

@Data
@Configuration
@EnableCaching
@ComponentScan("com.picpay.softwareengineerchallenge")
@EqualsAndHashCode(callSuper = true)
public class RedisConfiguration extends CachingConfigurerSupport {

    @Value("${cacheManager.strategy}")
    String cacheManagerStrategy;
    @Value("${cacheManager.user.expirationTime}")
    Long userCacheExpireTime;

    @Bean(name = "userCacheManager")
    public CacheManager userCacheManager(RedisConnectionFactory redisConnectionFactory) {
        if ("redis".equalsIgnoreCase(cacheManagerStrategy)) {
            final RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                    .defaultCacheConfig()
                    .entryTtl(Duration.ofSeconds(userCacheExpireTime));
            return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(redisCacheConfiguration).build();
        } else {
            return new ConcurrentMapCacheManager();
        }
    }

}
