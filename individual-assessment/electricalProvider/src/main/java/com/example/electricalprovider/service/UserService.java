package com.example.electricalprovider.service;

import com.example.electricalprovider.models.UserModel;
import com.example.electricalprovider.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class responsible for handling user data.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * Constructor for UserService.
     *
     * @param userRepository the repository for users
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Adds a new citizen for the given provider.
     *
     * @param providerId the ID of the provider
     * @param userModel the model containing the citizen's details
     * @return a message indicating the citizen was created successfully
     */
    public String addCitizen(String providerId, UserModel userModel) {
        // Retrieve the count of existing citizens for the given providerId
        long citizenCount = userRepository.countByProviderId(providerId);
        UserModel citizen = new UserModel();
        citizen.setProviderId(providerId);
        citizen.setDatabaseNo((int) (citizenCount + 1));
        citizen.setName(userModel.getName());
        citizen.setAddress(userModel.getAddress());
        citizen.setCity(userModel.getCity());
        citizen.setCountry(userModel.getCountry());
        citizen.setEmail(userModel.getEmail());
        citizen.setPhone(userModel.getPhone());
        citizen.setPostalCode(userModel.getPostalCode());
        citizen.setState(userModel.getState());

        String id = userRepository.save(citizen).getId();

        return ("Citizen created successfully with id: " + id);
    }

    /**
     * Retrieves all citizens for the given provider.
     *
     * @param providerId the ID of the provider
     * @return a list of all citizens for the given provider
     * @throws IllegalArgumentException if no citizens are found
     */
    public List<UserModel> getAllCitizens(String providerId) {
        return userRepository.findByProviderId(providerId)
                .orElseThrow(() -> new IllegalArgumentException("No citizens found"));
    }

    /**
     * Retrieves a citizen by ID for the given provider.
     *
     * @param providerId the ID of the provider
     * @param id the ID of the citizen
     * @return the model containing the citizen's details
     * @throws IllegalArgumentException if no citizen is found with the given ID
     */
    public UserModel getCitizenById(String providerId, String id) {
        return userRepository.findByProviderIdAndId(providerId, id)
                .orElseThrow(() -> new IllegalArgumentException("Citizen not found with id: " + id));
    }


    /**
     * Updates the data of an existing citizen for the given provider.
     *
     * @param providerId the ID of the provider
     * @param id the ID of the citizen
     * @param userModel the model containing the updated details
     * @return a message indicating the citizen was updated successfully
     * @throws IllegalArgumentException if no citizen is found with the given ID
     */
    public String updateCitizenData(String providerId, String id, UserModel userModel) {
        UserModel citizenInfo = userRepository.findByProviderIdAndId(providerId, id)
                .orElseThrow(() -> new IllegalArgumentException("Citizen not found with id: " + id));

        //Update only non-null fields
        updateNonNullFields(userModel, citizenInfo);

        userRepository.save(citizenInfo);
        return "Citizen updated successfully with id: " + id;

    }

    /**
     * Updates the non-null fields of the target model with values from the source model.
     *
     * @param source the source model containing the updated values
     * @param target the target model to be updated
     */
    private void updateNonNullFields(UserModel source, UserModel target) {
        Optional.ofNullable(source.getName()).ifPresent(target::setName);
        Optional.ofNullable(source.getAddress()).ifPresent(target::setAddress);
        Optional.ofNullable(source.getCity()).ifPresent(target::setCity);
        Optional.ofNullable(source.getCountry()).ifPresent(target::setCountry);
        Optional.ofNullable(source.getEmail()).ifPresent(target::setEmail);
        Optional.ofNullable(source.getPhone()).ifPresent(target::setPhone);
        Optional.ofNullable(source.getPostalCode()).ifPresent(target::setPostalCode);
        Optional.ofNullable(source.getState()).ifPresent(target::setState);
    }

    /**
     * Deletes a citizen by ID for the given provider.
     *
     * @param providerId the ID of the provider
     * @param id the ID of the citizen
     * @return a message indicating the citizen was deleted successfully
     */
    public String deleteCitizen(String providerId, String id) {
        userRepository.deleteByProviderIdAndId(providerId, id);
        return "Citizen deleted successfully with id: " + id;
    }
}
