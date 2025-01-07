package com.example.citizen.controller;

import com.example.citizen.dto.SmartMeterData;
import com.example.citizen.service.SmartMeterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing smart meter data.
 *
 * This controller provides endpoints for registering, automating, and retrieving smart meter data.
 * It interacts with the {@link SmartMeterService} to handle business logic
 */
@RestController
@RequestMapping("/api/citizen")
public class SmartMeterController {
    /**
     * Service for handling smart meter operations.
     */
    private final SmartMeterService smartMeterService;

    /**
     * Logger for logging events in the SmartMeterController.
     */
    private static final Logger logger = LoggerFactory.getLogger(SmartMeterController.class);

    /**
     * Constructor for SmartMeterController.
     *
     * @param smartMeterService the service to handle smart meter operations
     */
    @Autowired
    public SmartMeterController(SmartMeterService smartMeterService) {
        this.smartMeterService = smartMeterService;
    }

    @PostMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test citizen successful");
    }


    /**
     * Registers a smart meter for a user with a specific provider.
     * Called by electricalProvider to register a smartMeter for a user
     *
     * @param providerId the unique identifier of the provider
     * @param id the unique identifier of the user
     * @return a success message or an error message
     */
    @PostMapping("/providers/{providerId}/users/{id}/smartMeter")
    public ResponseEntity<String> registerSmartMeter(@PathVariable String  providerId, @PathVariable String id ) {
        try {
            String smartMeterData = smartMeterService.registerSmartMeter(providerId, id);
            return ResponseEntity.ok(smartMeterData);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred. Please try again later.");
        }
    }

    /**
     * Automates the smart meter reading and publishes the data to electricalProvider microservice.
     *
     * @param id the unique identifier of the user
     * @return a success or error message
     */
    @PostMapping("/users/{id}/smartMeter")
    public ResponseEntity<String> publishSmartMeter(@PathVariable String id) {
        try {
            smartMeterService.automateAndPublishSmartMeter(id);
            return ResponseEntity.ok("Smart meter data has been published successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to publish smart meter data: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An internal error occurred while fetching data.");
        }
    }

    /**
     * Retrieves smart meter data for a specific user.
     * Use DTO to return only the necessary data to the user
     *
     * @param id the unique identifier of the user
     * @return smart meter data or an error message
     */
    @GetMapping("/users/{id}/smartMeter")
    public ResponseEntity<?> getCustomerSmartMeterByID(@PathVariable String id) {
        try {
            SmartMeterData smartMeterUser = smartMeterService.getCustomerSmartMeterByID(id);
            return ResponseEntity.ok().body(smartMeterUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error initializing SmartMeter: " + e.getMessage());
        }catch (Exception e) {
            logger.error("Reason of error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching SmartMeter list: " + e.getMessage() + " please contact administrator for assistance");
        }
    }

    /**
     * Partially updates a smart meter's data with manual readings.
     *
     * @param id             the unique identifier of the smart meter
     * @param smartMeterData the manual reading data to be submitted
     * @return a success or error response
     */
    @PatchMapping("/users/{id}/smartMeter")
    public ResponseEntity<?> updateReadingManual(@PathVariable String id, @RequestBody SmartMeterData smartMeterData) {
        try {
            smartMeterService.submitManualReading(id, smartMeterData);
            return ResponseEntity.ok("Reading submitted successfully for SmartMeter: " + id +
                    ". Current consumption: " + smartMeterData.getCurrentConsumption());
        } catch (IllegalArgumentException e) {
            logger.warn("Validation error for manual reading submission: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Reason of error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error during manual reading submission: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error submitting reading: " + e.getMessage() +
                            ". \nPlease contact administrator for assistance");
        }
    }

}
