package com.example.electricalprovider.repository;

import com.example.electricalprovider.models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {
    Optional<List<UserModel>>findByProviderId(String providerId);
    Optional<UserModel> findByProviderIdAndId(String providerId, String id);
    void deleteByProviderIdAndId(String providerId, String id);

    long countByProviderId(String providerId);
}
