package com.example.electricalprovider.repository;

import com.example.electricalprovider.models.smartMeterModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SmartMeterRepository extends MongoRepository<smartMeterModel, String> {
    Optional<smartMeterModel> findTopByCustomerIdOrderByCurrentConsumptionDesc(String customerId);

    Optional<List<smartMeterModel>> findAllByCustomerIdAndProviderIdOrderByReadingTimestampDesc(String customerId, String providerId);

    Optional<List<smartMeterModel>> findAllByProviderIdOrderByReadingTimestampDesc(String providerId);
}
