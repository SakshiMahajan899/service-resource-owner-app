package com.backend.app.controller;

import com.backend.app.config.ServiceMapper;
import com.backend.app.dto.ApiResponse;
import com.backend.app.dto.ServiceDto;
import com.backend.app.model.Service;
import com.backend.app.service.ServiceManager;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller that exposes endpoints for managing Service entities.
 * Handles creation, updating, and fetching operations via HTTP methods.
 */
@RestController
@AllArgsConstructor
@RequestMapping("v1/services")
public class ServiceController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);

    private ServiceManager manager;

    /**
     * Creates and persists a Service object provided in the request body.
     * The object is validated, stored in the database, and cached.
     *
     * @param service the {@link Service} object sent in the request body
     * @return HTTP 201 with the created Service object, or error status on failure
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ServiceDto>> create(@RequestBody Service service) {
        logger.info("Request received: Create new Service");

        Service created = manager.createService(service);
        ServiceDto dto = ServiceMapper.toDto(created);
        ApiResponse<ServiceDto> response = new ApiResponse<>(true, dto, "Service created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Updates an existing Service object by ID and persists changes.
     * The updated object is also refreshed in the cache.
     *
     * @param id      the ID of the Service to update
     * @param updated the new data for the Service
     * @return HTTP 200 with updated Service or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ServiceDto>> update(@PathVariable String id, @RequestBody Service updated) {
        logger.info("Request received: Update Service with id {}", id);

        Service result = manager.updateService(id, updated);
        ServiceDto dto = ServiceMapper.toDto(result);
        ApiResponse<ServiceDto> response = new ApiResponse<>(true, dto, "Service updated successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a Service object by ID from cache or database.
     *
     * @param id the ID of the Service
     * @return HTTP 200 with the found Service or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ServiceDto>> fetch(@PathVariable String id) {
        logger.info("Request received: Fetch Service with id {}", id);

        Service service = manager.getService(id);
        ServiceDto dto = ServiceMapper.toDto(service);
        ApiResponse<ServiceDto> response = new ApiResponse<>(true, dto, "Service fetched successfully");
        return ResponseEntity.ok(response);
    }
}

