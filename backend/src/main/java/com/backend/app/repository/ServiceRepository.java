package com.backend.app.repository;

import com.backend.app.model.Service;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing {@link Service} entities in MongoDB.
 * Extends {@link MongoRepository} to provide CRUD operations.
 *
 * Custom query methods can be defined here if needed.
 */
@Repository
public interface ServiceRepository extends MongoRepository<Service, String> {
}
