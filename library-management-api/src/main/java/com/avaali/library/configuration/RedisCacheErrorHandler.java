package com.avaali.library.configuration;

import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.stereotype.Component;

@Component
public class RedisCacheErrorHandler implements CacheErrorHandler {

    @Override
    public void handleCacheGetError(
            RuntimeException exception,
            Cache cache,
            Object key) {

        System.out.println(
                "Redis GET failed. Continuing without cache: "
                        + exception.getMessage()
        );
    }

    @Override
    public void handleCachePutError(
            RuntimeException exception,
            Cache cache,
            Object key,
            Object value) {

        System.out.println(
                "Redis PUT failed. Continuing without cache: "
                        + exception.getMessage()
        );
    }

    @Override
    public void handleCacheEvictError(
            RuntimeException exception,
            Cache cache,
            Object key) {

        System.out.println(
                "Redis EVICT failed: "
                        + exception.getMessage()
        );
    }

    @Override
    public void handleCacheClearError(
            RuntimeException exception,
            Cache cache) {

        System.out.println(
                "Redis CLEAR failed: "
                        + exception.getMessage()
        );
    }
}