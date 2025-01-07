package com.example.citizen.service;

import com.example.citizen.dto.SmartMeterData;
import com.example.citizen.model.smartMeterModel;
import com.example.citizen.publisher.SmartMeterPublisher;
import com.example.citizen.repository.SmartMeterRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * Service for managing and processing smart meter data for every citizen.
 * This class provides methods for registering, retrieving, updating, and publishing
 * smart meter readings. It interacts with the MongoDB database for data storage
 * and RabbitMQ for message publishing.
 * Responsibilities:
 * Registers new smart meters for citizens.
 * Retrieves and updates smart meter readings.
 * Publishes updated smart meter data to RabbitMQ.
 * Validates ObjectId fields to ensure data integrity.
 *
 */
@Service
public class SmartMeterService {

    private static final Logger logger = LoggerFactory.getLogger(SmartMeterService.class);


    /**
     * Repository for interacting with MongoDB smart meter data collection.
     */
    private final SmartMeterRepository smartMeterRepository;

    /**
     * Publisher for sending smart meter data to RabbitMQ.
     */
    private final SmartMeterPublisher smartMeterPublisher;

    /**
     * Constructs a new instance of the `SmartMeterService`.
     *
     * @param smartMeterRepository the repository for MongoDB operations
     * @param smartMeterPublisher the publisher for RabbitMQ operations
     */
    @Autowired
    public SmartMeterService(SmartMeterRepository smartMeterRepository, SmartMeterPublisher smartMeterPublisher) {
        this.smartMeterRepository = smartMeterRepository;
        this.smartMeterPublisher = smartMeterPublisher;
    }

    /**
     * Registers a new smart meter for a citizen. (initiated by electrical provider)
     *
     * @param providerId the unique identifier of the electricity provider
     * @param id the unique identifier of the citizen
     * @return a success message if registration is successful
     * @throws IllegalArgumentException if any validation or database operation fails
     */
    public String registerSmartMeter(String providerId, String id) {
        validateObjectId(id, "Citizen ID");
        validateObjectId(providerId, "Provider ID");

        if (smartMeterRepository.findByCustomerId(id).isPresent()) {
            throw new IllegalArgumentException("Smart meter already registered for citizen ID: " + id);
        }

        try {
            smartMeterModel smartMeterModel = new smartMeterModel();
            smartMeterModel.setCustomerId(id);
            smartMeterModel.setProviderId(providerId);
            smartMeterModel.setReadingTimestamp(LocalDateTime.now());
            smartMeterModel.setAutomatedEntryMethod(true);
            smartMeterModel.setAlertFlag(false);
            smartMeterModel.setCurrentConsumption(0.0);
            smartMeterRepository.save(smartMeterModel);
            return "Smart Meter registered successfully";
        } catch (Exception e) {
            logger.error("Error registering smart meter for citizen ID: {}", id, e);
            throw new RuntimeException("Unexpected error while registering smart meter", e);
        }
    }


    /**
     * Automates the smart meter reading process and publishes the updated data to RabbitMQ.
     * This method retrieves the smart meter reading based on customerId, generates an incremental
     * consumption value, updates the reading, and publishes the updated data.
     *
     * @param id the unique identifier of the citizen
     * @throws IllegalArgumentException if the citizen ID is invalid or no data is found
     */
    public void automateAndPublishSmartMeter(String id) {
        validateObjectId(id, "Citizen ID");
        smartMeterModel latestReading = smartMeterRepository.findByCustomerId(id)
                    .orElseThrow(() -> new IllegalArgumentException("No readings found for customer ID: " + id));
        System.out.println("latestReading: " + latestReading.toString());

        // Generate a random increment between 0.01 and 0.3 kWh
        Random random = new Random();
        double increment = 0.01 + (0.3 - 0.01) * random.nextDouble();

        //Update smart Meter data with the incremented consumption
        latestReading.setReadingTimestamp(LocalDateTime.now());
        latestReading.setAutomatedEntryMethod(true);
        latestReading.setCurrentConsumption(latestReading.getCurrentConsumption() + increment);
        latestReading.setAlertFlag(false);

        smartMeterModel newData=smartMeterRepository.save(latestReading);
        // Publish the data to RabbitMQ
        smartMeterPublisher.publishSmartMeterData(newData);
    }

    /**
     * Validates if the given ID is a valid MongoDB ObjectId.
     *
     * @param id the ID to validate
     * @param fieldName the name of the field for error reporting
     * @throws IllegalArgumentException if the ID is not a valid ObjectId
     */
    private void validateObjectId(String id, String fieldName) {
        if (!ObjectId.isValid(id)) {
            throw new IllegalArgumentException("Invalid ObjectId for " + fieldName + ": " + id);
        }
    }

    /**
     * Retrieves the smart meter data for a specific citizen.
     *
     * @param id the unique identifier of the citizen
     * @return the smart meter data in a DTO format
     * @throws IllegalArgumentException if the citizen ID is invalid or no data is found
     */
    public SmartMeterData getCustomerSmartMeterByID(String id){
        validateObjectId(id, "Citizen ID");
        smartMeterModel model=smartMeterRepository.findByCustomerId(id)
                .orElseThrow(()->new IllegalArgumentException("No readings found for customer ID: " + id));


        return smartMeterModelToResponse(model);
    }
    /**
     * Converts a `smartMeterModel` object into a `SmartMeterData` DTO.
     *
     * @param model the smart meter model to convert
     * @return a DTO representation of the smart meter data
     */
    private SmartMeterData smartMeterModelToResponse(smartMeterModel model) {
        SmartMeterData response = new SmartMeterData();
        response.setCurrentConsumption(model.getCurrentConsumption());
        response.setReadingTimestamp(model.getReadingTimestamp());
        response.setAlertFlag(model.isAlertFlag());
        response.setAutomatedEntryMethod(model.isAutomatedEntryMethod());
        return response;
    }
    /**
     * Submits a manual reading for a smart meter.
     *
     * <p>This method validates the new reading, updates the smart meter data,
     * and publishes the updated data to RabbitMQ.</p>
     *
     * @param id the unique identifier of the citizen
     * @param smartMeterData the new smart meter reading data
     * @throws IllegalArgumentException if the new reading is invalid or less than the current reading
     */
    public void submitManualReading(String id, SmartMeterData smartMeterData){
        validateObjectId(id, "Citizen ID");

        try {
            double newConsumption = smartMeterData.getCurrentConsumption();
            smartMeterModel latestReading = smartMeterRepository.findByCustomerId(id).orElseThrow(() -> new IllegalArgumentException("No readings found for customer ID: " + id));
            if (newConsumption <= latestReading.getCurrentConsumption()) {
                throw new IllegalArgumentException("New consumption value must be greater than the current consumption.");
            }
            latestReading.setCurrentConsumption(newConsumption);
            latestReading.setReadingTimestamp(LocalDateTime.now());
            latestReading.setAutomatedEntryMethod(false);
            smartMeterModel data =smartMeterRepository.save(latestReading);

            //publish the updated data to RabbitMQ
            smartMeterPublisher.publishSmartMeterData(data);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error submitting manual reading: " + e.getMessage());
        }
    }

}