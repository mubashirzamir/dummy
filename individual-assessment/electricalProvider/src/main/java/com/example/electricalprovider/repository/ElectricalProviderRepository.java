package com.example.electricalprovider.repository;

import com.example.electricalprovider.models.ElectricalProviderModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectricalProviderRepository extends MongoRepository<ElectricalProviderModel, String> {

}
