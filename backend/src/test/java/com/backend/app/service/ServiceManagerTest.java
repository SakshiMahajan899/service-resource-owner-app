package com.backend.app.service;


import com.backend.app.cache.ServiceCache;
import com.backend.app.config.IdGenerator;
import com.backend.app.exception.InvalidServiceException;
import com.backend.app.exception.ServiceNotFoundException;
import com.backend.app.model.Owner;
import com.backend.app.model.Resource;
import com.backend.app.model.Service;
import com.backend.app.repository.ServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServiceManagerTest {

    @Mock
    private ServiceRepository repository;

    @Mock
    private ServiceCache cache;

    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private ServiceManager manager;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateService_success() {
        Service service = new Service();
        Resource resource = new Resource();
        Owner owner = new Owner();
        resource.setOwners(List.of(owner));
        service.setResources(List.of(resource));

        String serviceId = "service-1";
        String resourceId = "resource-1";
        String ownerId = "owner-1";

        when(idGenerator.generateServiceId(anyInt())).thenReturn(serviceId);
        when(idGenerator.generateResourceId(anyInt(), anyInt())).thenReturn(resourceId);
        when(idGenerator.generateOwnerId(anyInt(), anyInt(), anyInt())).thenReturn(ownerId);
        when(cache.contains(serviceId)).thenReturn(false);
        when(repository.existsById(serviceId)).thenReturn(false);
        when(repository.save(any(Service.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Service result = manager.createService(service);

        assertEquals(serviceId, result.getId());
        assertEquals(resourceId, result.getResources().get(0).getId());
        assertEquals(ownerId, result.getResources().get(0).getOwners().get(0).getId());
        verify(cache).put(eq(serviceId), any(Service.class));
    }

    @Test
    public void testCreateService_duplicateId_throwsException() {
        Service service = new Service();
        String serviceId = "duplicate-id";

        when(idGenerator.generateServiceId(anyInt())).thenReturn(serviceId);
        when(cache.contains(serviceId)).thenReturn(true);

        assertThrows(InvalidServiceException.class, () -> manager.createService(service));
    }

    @Test
    public void testUpdateService_success() {
        String serviceId = "svc-123";
        Service updated = new Service();

        when(repository.existsById(serviceId)).thenReturn(true);
        when(repository.save(updated)).thenReturn(updated);

        Service result = manager.updateService(serviceId, updated);

        assertEquals(serviceId, result.getId());
        verify(cache).put(serviceId, updated);
    }

    @Test
    public void testUpdateService_notFound_throwsException() {
        String serviceId = "missing-id";
        Service updated = new Service();

        when(repository.existsById(serviceId)).thenReturn(false);

        assertThrows(ServiceNotFoundException.class, () -> manager.updateService(serviceId, updated));
    }

    @Test
    public void testGetService_cacheHit() {
        String id = "cached-id";
        Service service = new Service();
        when(cache.contains(id)).thenReturn(true);
        when(cache.get(id)).thenReturn(service);

        Service result = manager.getService(id);
        assertSame(service, result);
    }

    @Test
    public void testGetService_dbHit() {
        String id = "db-id";
        Service service = new Service();

        when(cache.contains(id)).thenReturn(false);
        when(repository.findById(id)).thenReturn(Optional.of(service));

        Service result = manager.getService(id);
        assertSame(service, result);
        verify(cache).put(id, service);
    }

    @Test
    public void testGetService_notFound_throwsException() {
        String id = "unknown-id";

        when(cache.contains(id)).thenReturn(false);
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ServiceNotFoundException.class, () -> manager.getService(id));
    }
}

