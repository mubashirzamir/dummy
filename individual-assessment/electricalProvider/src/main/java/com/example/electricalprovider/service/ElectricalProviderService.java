package com.example.electricalprovider.service;

import com.example.electricalprovider.dto.ElectricalProviderUpdateDTO;
import com.example.electricalprovider.models.ElectricalProviderModel;
import com.example.electricalprovider.repository.ElectricalProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class responsible for handling electrical provider data.
 */
@Service
public class ElectricalProviderService {

    private final ElectricalProviderRepository ElectricalProviderRepository;

    /**
     * Constructor for ElectricalProviderService.
     *
     * @param ElectricalProviderRepository the repository for electrical provider data
     */
    @Autowired
    public ElectricalProviderService(ElectricalProviderRepository ElectricalProviderRepository) {
        this.ElectricalProviderRepository = ElectricalProviderRepository;
    }

    /**
     * Checks if the electrical provider registration data is valid.
     *
     * @param electricalProviderModel the model containing electrical provider data
     */
    private static void registrationErrorChecker(ElectricalProviderModel electricalProviderModel) {
        if (electricalProviderModel.getCompanyName() == null) {
            throw new IllegalArgumentException("Company name is required");
        }
        if (electricalProviderModel.getCompanyAddress() == null) {
            throw new IllegalArgumentException("Company address is required");
        }
        if (electricalProviderModel.getCompanyPhoneNumber() == null) {
            throw new IllegalArgumentException("Company phone number is required");
        }
        if (electricalProviderModel.getCompanyEmail() == null) {
            throw new IllegalArgumentException("Company email is required");
        }
    }

    /**
     * Registers a new electrical provider.
     *
     * @param electricalProviderModel the model containing electrical provider data
     */
    public void registerProvider(ElectricalProviderModel electricalProviderModel) {
        if (ElectricalProviderRepository.existsById(electricalProviderModel.getId())) {
            throw new IllegalArgumentException("Provider with the same ID already exists");
        }
        registrationErrorChecker(electricalProviderModel);
        // Save the electricalProviderModel to the database
        ElectricalProviderRepository.save(electricalProviderModel);

    }

    /**
     * Retrieves an electrical provider by ID.
     *
     * @param providerId the ID of the electrical provider
     * @return the electrical provider data
     */
    public ElectricalProviderUpdateDTO getProvider(String providerId) {
        // Fetch the electricalProviderModel from the database
        ElectricalProviderModel model=ElectricalProviderRepository.findById(providerId)
                .orElseThrow(() -> new IllegalArgumentException("Citizen not found with id: " + providerId));

        ElectricalProviderUpdateDTO electricalProviderUpdateDTO = new ElectricalProviderUpdateDTO();
        electricalProviderUpdateDTO.setCompanyName(model.getCompanyName());
        electricalProviderUpdateDTO.setCompanyEmail(model.getCompanyEmail());
        electricalProviderUpdateDTO.setCompanyAddress(model.getCompanyAddress());
        electricalProviderUpdateDTO.setCompanyPhoneNumber(model.getCompanyPhoneNumber());

        return electricalProviderUpdateDTO;
    }

    /**
     * Updates an existing electrical provider.
     *
     * @param providerId the ID of the electrical provider
     * @param updateDTO the DTO containing the updated electrical provider data
     */
    public void updateProvider(String providerId, ElectricalProviderUpdateDTO updateDTO) {
        ElectricalProviderModel existingProvider = ElectricalProviderRepository.findById(providerId)
                .orElseThrow(() -> new IllegalArgumentException("Provider with ID " + providerId + " not found"));

        // Update fields if provided
        if (updateDTO.getCompanyName() != null) existingProvider.setCompanyName(updateDTO.getCompanyName());
        if (updateDTO.getCompanyEmail() != null) existingProvider.setCompanyEmail(updateDTO.getCompanyEmail());
        if (updateDTO.getCompanyAddress() != null) existingProvider.setCompanyAddress(updateDTO.getCompanyAddress());
        if (updateDTO.getCompanyPhoneNumber() != null) existingProvider.setCompanyPhoneNumber(updateDTO.getCompanyPhoneNumber());

        // Validate and save the updated provider
        registrationErrorChecker(existingProvider);
        ElectricalProviderRepository.save(existingProvider);
    }

    /**
     * Deletes an existing electrical provider.
     *
     * @param providerId the ID of the electrical provider
     */
    public void deleteProvider(String providerId) {
        // Delete the electricalProviderModel from the database
        ElectricalProviderRepository.deleteById(providerId);
    }

    //DESIGNED FOR PURPOSE OF OBTAINING ALL ELECTRICAL PROVIDER INFO
    public List<ElectricalProviderModel> getAllElectricalProviderInfo(){
        return ElectricalProviderRepository.findAll();
    }

    public void registerSampleProvider() {
        // Sample providers data
        ElectricalProviderModel provider1 = new ElectricalProviderModel();
        provider1.setId("507f1f77bcf86cd799439010");
        provider1.setCompanyName("PowerGrid Inc.");
        provider1.setCompanyAddress("123 Energy St, Power City");
        provider1.setCompanyPhoneNumber("123-456-7890");
        provider1.setCompanyEmail("contact@powergrid.com");

        ElectricalProviderModel provider2 = new ElectricalProviderModel();
        provider2.setId("507f1f77bcf86cd799439011");
        provider2.setCompanyName("GreenEnergy Solutions");
        provider2.setCompanyAddress("456 Solar Ave, Green Town");
        provider2.setCompanyPhoneNumber("234-567-8901");
        provider2.setCompanyEmail("info@greenenergy.com");

        ElectricalProviderModel provider3 = new ElectricalProviderModel();
        provider3.setId("507f1f77bcf86cd799439012");
        provider3.setCompanyName("FuturePower Systems");
        provider3.setCompanyAddress("789 Innovation Blvd, Future City");
        provider3.setCompanyPhoneNumber("345-678-9012");
        provider3.setCompanyEmail("support@futurepower.com");

        // Save to the repository
        ElectricalProviderRepository.save(provider1);
        ElectricalProviderRepository.save(provider2);
        ElectricalProviderRepository.save(provider3);
    }
}
