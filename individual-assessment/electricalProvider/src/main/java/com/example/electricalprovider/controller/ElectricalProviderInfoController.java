package com.example.electricalprovider.controller;

import com.example.electricalprovider.dto.ElectricalProviderUpdateDTO;
import com.example.electricalprovider.models.ElectricalProviderModel;
import com.example.electricalprovider.service.ElectricalProviderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing electrical provider information.
 */
@RestController
@RequestMapping("/api/electricalProvider")
public class ElectricalProviderInfoController {

    private final ElectricalProviderService ElectricalProviderService;

    /**
     * Constructor for ElectricalProviderInfoController.
     *
     * @param ElectricalProviderService the service for managing electrical provider data
     */
    public ElectricalProviderInfoController(ElectricalProviderService ElectricalProviderService) {
        this.ElectricalProviderService = ElectricalProviderService;
    }

    @PostMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test electrical provider successful");
    }

    /**
     * Registers a new electrical provider.
     *
     * @param ElectricalProviderModel the electrical provider to register
     * @return a success message or an error message
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerProvider(@RequestBody ElectricalProviderModel ElectricalProviderModel){
        try{
            // Save the provider to the database
            ElectricalProviderService.registerProvider(ElectricalProviderModel);
            return ResponseEntity.ok("Provider registered successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/providerInfo")
    public List<ElectricalProviderModel> getAllElectricalProviderInfo(){
        return ElectricalProviderService.getAllElectricalProviderInfo();
    }

    /**
     * Retrieves an electrical provider by ID.
     *
     * @param providerId the ID of the provider to retrieve
     * @return ResponseEntity with the provider data or error message
     */
    @GetMapping("/provider/{id}")
    public ResponseEntity<?> getProvider(@PathVariable("id") String providerId) {
        try{
            // Fetch the provider from the database
            ElectricalProviderUpdateDTO data=ElectricalProviderService.getProvider(providerId);
            return ResponseEntity.ok(data);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error reason: "+e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reason: "+e.getMessage());
        }
    }
    /**
     * Updates an existing electrical provider.
     *
     * @param providerId the ID of the provider to update
     * @param ElectricalProviderUpdateDTO the DTO containing updated provider data
     * @return ResponseEntity with a success message or error message
     */
    @PutMapping("/provider/{id}")
    public ResponseEntity<String> updateProvider(@PathVariable("id")  String providerId, @RequestBody ElectricalProviderUpdateDTO ElectricalProviderUpdateDTO) {
        try{
            // Update the provider in the database
            ElectricalProviderService.updateProvider(providerId, ElectricalProviderUpdateDTO);
            return ResponseEntity.ok("Provider updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error reason: "+e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reason: "+e.getMessage());
        }
    }
    /**
     * Deletes an electrical provider by ID.
     *
     * @param providerId the ID of the provider to delete
     * @return ResponseEntity with a success message or error message
     */
    @DeleteMapping("/provider/{id}")
    public ResponseEntity<String>deleteProvider(@PathVariable("id")  String providerId) {
        try{
            // Delete the provider from the database
            ElectricalProviderService.deleteProvider(providerId);
            return ResponseEntity.ok("Provider deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error reason: "+e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reason: "+e.getMessage());
        }
    }

    @PostMapping("/sampleRegister")
    public ResponseEntity<String> registerSampleProvider(){
        try{
            // Save the provider to the database
            ElectricalProviderService.registerSampleProvider();
            return ResponseEntity.ok("Provider registered successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
