package com.example.SmartCity.controller;

import com.example.SmartCity.dto.*;
import com.example.SmartCity.model.ElectricalProviderConsumptionSummary;
import com.example.SmartCity.service.SmartCityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing electrical provider consumption summaries.
 */
@RestController
@RequestMapping("/api/smartCity")
public class SmartCityController {

    private static final Logger logger = LoggerFactory.getLogger(SmartCityController.class);

    private final SmartCityService smartCityService;
    /**
     * Constructor for SmartCityController.
     *
     * @param smartCityService the service to handle business logic for smart city operations
     */
    public SmartCityController(SmartCityService smartCityService) {
        this.smartCityService = smartCityService;
    }

    /**
     * Endpoint to save the electrical provider consumption summary.
     *
     * @param providerId the ID of the electrical provider
     * @return ResponseEntity containing the saved summary or an error message
     */
    @PostMapping("/electricalProvider/{providerId}/summary")
    public ResponseEntity<?> saveElectricalProviderSummary(@PathVariable String providerId) {
        try {
            ElectricalProviderConsumptionSummary summary = smartCityService.fetchAndSaveElectricalProviderSummary(providerId);
            return ResponseEntity.ok(summary);
        } catch (IllegalArgumentException e) {
            // Log the exception
            logger.error("Invalid argument: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Log the exception
            logger.error("An error occurred: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred. Please try again later.");
        }
    }
    @PostMapping("/electricalProvider/{providerId}/allSummary")
    public ResponseEntity<?> saveAllElectricalProviderSummary(@PathVariable String providerId) {
        try {
            List<ElectricalProviderConsumptionSummary> summary = smartCityService.fetchAndSaveAllElectricalProviderSummary(providerId);
            return ResponseEntity.ok(summary);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred. Please try again later.");
        }
    }
    @PostMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test smart city successful");
    }

    /**
     * Endpoint to update the electrical provider consumption summary.
     *
     * @param providerId the ID of the electrical provider
     * @return ResponseEntity containing the updated summary or an error message
     */
    @PutMapping("/electricalProvider/{providerId}/summary")
    public ResponseEntity<?> updateElectricalProviderSummary(@PathVariable String providerId) {
        try {
            ElectricalProviderConsumptionSummary summary = smartCityService.updateElectricalProviderSummary(providerId);
            return ResponseEntity.ok(summary);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred. Please try again later.");
        }
    }

    /**
     * Endpoint to save the electrical provider consumption summary.
     *
     * @param providerId the ID of the electrical provider
     * @return ResponseEntity containing the saved summary or an error message
     */
    @GetMapping("/electricalProvider/{providerId}/summary")
    public ResponseEntity<?> viewIndividualProviderSummary(@PathVariable String providerId) {
        try {
            List<ElectricalProviderConsumptionSummary> summary = smartCityService.viewIndividualProviderSummary(providerId);
            return ResponseEntity.ok(summary);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred. Please try again later.");
        }
    }

    //EDITED FOR SAKE OF PART2, HENCE ADDITIONAL FEATURES BUT NOT INCLUDED IN MARKS:
    @GetMapping("/summary")
    public ResponseEntity<?> getSummary() {
        try {
            List<ElectricalProviderConsumptionSummary> summary = smartCityService.getAllCitySummary();
            return ResponseEntity.ok(summary);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred. Please try again later.");
        }
    }

    //MACAM WRONG
    @GetMapping("/data/providers")
    public ResponseEntity<?> getAggregatedByProvider(@RequestParam(defaultValue = "LAST_30_DAYS") String timeRange) {
        try{
            List<ConsumptionByProviderSummary> result= smartCityService.getAggregatedByProvider(timeRange);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred. Please try again later.");
        }
    }
    //not working
    @GetMapping("/data/city")
    public ResponseEntity<?> getAggregatedForCity(@RequestParam(defaultValue = "LAST_30_DAYS") String timeRange) {
        try {
            ConsumptionForCitySummary summary = smartCityService.getAggregatedForCity(timeRange);
            return ResponseEntity.ok(summary);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred. Please try again later.");
        }
    }
    //not working
    @GetMapping("/graphs/monthly-average/providers")
    public ResponseEntity<?> getMonthlyAverageByProvider(@RequestParam(defaultValue = "2024") int year) {
        try {
            List<MonthlyAverageByProviderSummary> summary = smartCityService.getMonthlyAverageByProvider(year);
            return ResponseEntity.ok(summary);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred. Please try again later.");
        }
    }

    @GetMapping("/graphs/monthly-average/city")
    public ResponseEntity<?> getMonthlyAverageForCity(@RequestParam(defaultValue = "2024") int year) {
        try {
            List<MonthlyAverageForCitySummary> result = smartCityService.getMonthlyAverageForCity(year);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred. Please try again later.");
        }
    }

    @GetMapping("/summary/date")
    public ResponseEntity<?> getSummaryByDate(@RequestParam(defaultValue = "LAST_30_DAYS") String timeRange) {
        try {
            List<ElectricalProviderConsumptionSummary> summary = smartCityService.getDataSummaryByDate(timeRange);
            return ResponseEntity.ok(summary);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred. Please try again later.");
        }
    }


    @GetMapping("/providerInfo")
    public ResponseEntity<?>getProviderInfo(){
        try {
            List<ElectricalProviderModel> summary = smartCityService.getProviderInfo();
            return ResponseEntity.ok(summary);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred. Please try again later.");
        }
    }
}
