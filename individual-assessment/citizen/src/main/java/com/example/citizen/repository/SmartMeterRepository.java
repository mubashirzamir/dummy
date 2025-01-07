package com.example.citizen.repository;

import com.example.citizen.model.smartMeterModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

/**
 * Repository interface for managing smart meter data in MongoDB.
 *
 *This interface extends {@link MongoRepository}, which provides CRUD and query methods
 * for working with smart meter data stored in MongoDB.
 *
 *Responsibilities:
 * Basic CRUD operations for smart meter data.
 * Custom query methods for specific use cases.
 *
 * @see smartMeterModel
 */
public interface SmartMeterRepository extends MongoRepository<smartMeterModel, String> {
    /**
     * Finds the smart meter data for a specific customer by their unique ID.
     *
     * @param customerId the unique identifier of the customer
     * @return an {@link Optional} containing the smart meter data if found, or empty if not
     */
    Optional<smartMeterModel> findByCustomerId(String customerId);
}
