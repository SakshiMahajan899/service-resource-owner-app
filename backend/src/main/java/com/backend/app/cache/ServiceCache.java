package com.backend.app.cache;

import com.backend.app.exception.CacheMissException;
import com.backend.app.model.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
/**
 * Thread-safe LRU (Least Recently Used) in-memory cache for {@link Service} entities.
 * <p>
 * Uses {@link LinkedHashMap} with access order to automatically evict the least recently accessed
 * entry when the cache size exceeds {@code MAX_CACHE_SIZE}.
 */
@Component
public class ServiceCache {

    /** Logger for internal cache operations and diagnostics. */
    private static final Logger logger = LoggerFactory.getLogger(ServiceCache.class);

    /** Maximum number of entries the cache can hold before evicting. */
    private static final int MAX_CACHE_SIZE = 5;

    /**
     * Internal LinkedHashMap-based cache with LRU eviction policy.
     * <p>
     * Configured to order entries by access and override {@link LinkedHashMap}
     * to automatically evict the least recently used entry when capacity is exceeded.
     */
    private final Map<String, Service> cache = new LinkedHashMap<>(MAX_CACHE_SIZE, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, Service> eldest) {
            boolean shouldEvict = size() > MAX_CACHE_SIZE;
            if (shouldEvict) {
                logger.info("Evicting least recently used Service with id: {}", eldest.getKey());
            }
            return shouldEvict;
        }
    };

    /**
     * Adds or updates a {@link Service} entity in the cache.
     * <p>
     * If the cache is at capacity, this may trigger eviction of the least recently used entry.
     *
     * @param id the unique identifier of the service
     * @param service the {@link Service} instance to cache
     */
    public synchronized void put(String id, Service service) {
        cache.put(id, service);
        logger.debug("Cached Service with id: {}", id);
    }

    /**
     * Retrieves a {@link Service} entity from the cache using its ID.
     * <p>
     * Accessing an item will update its LRU order.
     *
     * @param id the unique identifier of the service
     * @return the {@link Service} entity associated with the given ID
     * @throws CacheMissException if the ID is not present in the cache
     */
    public synchronized Service get(String id) {
        if (!cache.containsKey(id)) {
            logger.warn("Cache miss for Service id: {}", id);
            throw new CacheMissException(id);
        }
        logger.debug("Cache hit for Service id: {}", id);
        return cache.get(id);
    }

    /**
     * Checks whether the cache contains a {@link Service} entity with the given ID.
     *
     * @param id the unique identifier of the service
     * @return {@code true} if the service is cached, {@code false} otherwise
     */
    public synchronized boolean contains(String id) {
        boolean exists = cache.containsKey(id);
        logger.trace("Cache contains id {}: {}", id, exists);
        return exists;
    }
}
