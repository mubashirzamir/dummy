package com.example.electricalprovider.controller;

import com.example.electricalprovider.dto.ProviderSmartMeterSummary;
import com.example.electricalprovider.dto.UserSmartMeterReport;
import com.example.electricalprovider.service.SmartMeterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing smart meter information related to an electrical provider.
 */
@RestController
@RequestMapping("/api/electricalProvider/{providerId}/smartMeter")
public class SmartMeterController {

    public final SmartMeterService smartMeterService;

    /**
     * Constructor for SmartMeterController.
     *
     * @param smartMeterService the service for managing smart meter data
     */
    @Autowired
    public SmartMeterController(SmartMeterService smartMeterService) {

        this.smartMeterService = smartMeterService;
    }

    private static final Logger logger = LoggerFactory.getLogger(SmartMeterController.class);


    /**
     * Registers a smart meter for a user.
     *
     * @param providerId the ID of the provider
     * @param id the ID of the user
     * @return ResponseEntity with a success message or error message
     */
    @PostMapping("/register/users/{id}")
    public ResponseEntity<String> registerSmartMeter(@PathVariable String providerId, @PathVariable String id) {
        try {
            String message = smartMeterService.postRegistrationSmartMeter(providerId, id);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred. Please try again later.");
        }
    }

    /**
     * Retrieves summary smart meter data for a user.
     *
     * @param providerId the ID of the provider
     * @param customerId the ID of the customer
     * @return ResponseEntity with the summary data or error message
     */
    @GetMapping("/users/{customerId}/summary")
    public ResponseEntity<?> getSummarySmartMeterDataByUser(@PathVariable String providerId, @PathVariable String customerId) {
        try {
            UserSmartMeterReport message = smartMeterService.summarySmartMeterDataByUser(providerId, customerId);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred. Please try again later.");
        }
    }

    /**
     * Retrieves summary smart meter data for an electrical provider.
     *
     * @param providerId the ID of the provider
     * @return ResponseEntity with the summary data or error message
     */
    @GetMapping("/details")
    public ResponseEntity<?> getSummaryElectricalProviderSmartMeterData(@PathVariable String providerId) {
        try {
            ProviderSmartMeterSummary message = smartMeterService.summarySmartMeterDataByProvider(providerId);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred. Please try again later.");
        }
    }


    @GetMapping("/allDetails")
    public ResponseEntity<?> getAllSummaryElectricalProviderSmartMeterData(@PathVariable String providerId) {
        try {
            List<ProviderSmartMeterSummary> message = smartMeterService.summaryAllSmartMeterDataByProvider(providerId);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred. Please try again later.");
        }
    }
    
}
