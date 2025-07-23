package com.backend.app.service;

import com.backend.app.cache.ServiceCache;
import com.backend.app.config.IdGenerator;
import com.backend.app.exception.InvalidServiceException;
import com.backend.app.exception.ServiceNotFoundException;
import com.backend.app.model.Owner;
import com.backend.app.model.Resource;
import com.backend.app.model.Service;
import com.backend.app.repository.ServiceRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Service layer for handling business logic related to {@link Service} entities.
 * Interacts with both repository and cache, ensuring cache-aware operations.
 */
@AllArgsConstructor
@Component
public class ServiceManager {

    private static final Logger logger = LoggerFactory.getLogger(ServiceManager.class);

    private final ServiceRepository repository;
    private final ServiceCache cache;
    private final IdGenerator idGenerator;

    private static final AtomicInteger serviceCounter = new AtomicInteger(1);

    /**
     * Creates and persists a new {@link Service} object with auto-generated IDs.
     *
     * @param service the {@link Service} object to create
     * @return the persisted Service object
     */
    public Service createService(Service service) {
        int serviceIndex = serviceCounter.getAndIncrement();
        String serviceId = idGenerator.generateServiceId(serviceIndex);

        if (cache.contains(serviceId) || repository.existsById(serviceId)) {
            logger.warn("Service with id {} already exists in cache or DB", serviceId);
            throw new InvalidServiceException("Service with the generated ID already exists.");
        }

        service.setId(serviceId);

        List<Resource> inputResources = Optional.ofNullable(service.getResources())
                .orElse(Collections.emptyList());

        List<Resource> updatedResources = IntStream.range(0, inputResources.size())
                .mapToObj(i -> updateResource(inputResources.get(i), serviceIndex, i + 1))
                .collect(Collectors.toList());

        service.setResources(updatedResources);

        Service saved = repository.save(service);
        cache.put(saved.getId(), saved);

        logger.info("New Service created with generated id: {}", saved.getId());
        return saved;
    }

    private Resource updateResource(Resource resource, int serviceIndex, int resourceIndex) {
        resource.setId(idGenerator.generateResourceId(serviceIndex, resourceIndex));

        List<Owner> inputOwners = Optional.ofNullable(resource.getOwners())
                .orElse(Collections.emptyList());

        List<Owner> updatedOwners = IntStream.range(0, inputOwners.size())
                .mapToObj(j -> updateOwner(inputOwners.get(j), serviceIndex, resourceIndex, j + 1))
                .collect(Collectors.toList());

        resource.setOwners(updatedOwners);
        return resource;
    }

    private Owner updateOwner(Owner owner, int serviceIndex, int resourceIndex, int ownerIndex) {
        owner.setId(idGenerator.generateOwnerId(serviceIndex, resourceIndex, ownerIndex));
        return owner;
    }



    public Service updateService(String id, Service updated) {
        logger.info("Attempting to update Service with id: {}", id);

        if (!repository.existsById(id)) {
            logger.warn("No Service found with id: {}", id);
            throw new ServiceNotFoundException(id);
        }

        updated.setId(id);
        Service saved = repository.save(updated);
        cache.put(id, saved);

        logger.info("Service updated and cached for id: {}", id);
        return saved;
    }

    public Service getService(String id) {
        logger.info("Retrieving Service with id: {}", id);

        if (cache.contains(id)) {
            logger.info("Cache hit for Service id: {}", id);
            return cache.get(id);
        }

        return repository.findById(id)
                .map(service -> {
                    cache.put(id, service);
                    logger.info("Cache miss — loaded from DB: {}", id);
                    return service;
                })
                .orElseThrow(() -> {
                    logger.warn("Service not found in DB: {}", id);
                    return new ServiceNotFoundException(id);
                });
    }
}
