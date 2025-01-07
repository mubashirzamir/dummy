package com.example.citizen.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents the smart meter data model for MongoDB storage and API communication.
 *
 * This model is used to store and retrieve information about smart meter readings,
 * including customer and provider IDs, consumption data, timestamps, and metadata flags.
 * It supports serialization for RabbitMQ messaging.
 *
 * Key Features:
 *
 * Persistence: Annotated with MongoDB-specific annotations for database operations.
 * Serialization: Supports JSON serialization for RabbitMQ message exchange.
 * Fields: Tracks consumption, timestamps, and flags for automation and alerts.
 *
 */
@Document(value = "smartMeterData")
public class smartMeterModel implements Serializable {

    /**
     * The unique identifier for the smart meter reading (RabbitMQ).
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The unique identifier for the smart meter reading (MongoDB document ID).
     */
    @Id
    @JsonProperty("id")
    private String id;

    /**
     * The unique identifier for the customer associated with this smart meter reading.
     * Assigned by electricalProvider
     */
    @Field("Customer ID")
    @JsonProperty("customerId")
    private String customerId;

    /**
     * The unique identifier for the electricity provider associated with this smart meter reading.
     * Links to electricalProvider
     */
    @Field("Provider ID")
    @JsonProperty("providerId")
    private String providerId;

    /**
     * User current power consumption recorded by the smart meter, in kilowatt-hours (kWh).
     */
    @Field("currentConsumption")
    @JsonProperty("currentConsumption")
    private Double currentConsumption = null; // in kWh

    /**
     * The timestamp when the smart meter reading was recorded.
     */
    @Field("readingTimestamp")
    @JsonProperty("readingTimestamp")
    private LocalDateTime readingTimestamp = null;

    /**
     * Indicates whether the reading was recorded automatically.
     * True for automated readings; false for manual readings.
     */
    @Field("AutomatedEntryMethod")
    @JsonProperty("automatedEntryMethod")
    private boolean automatedEntryMethod = true;

    /**
     * Indicates whether an alert is associated with the current reading.
     * True if there is an alert; false otherwise.
     */
    @Field("alertFlag")
    @JsonProperty("alertFlag")
    private boolean alertFlag = false; // true if there's an alert

    /**
     * Default constructor required for deserialization and MongoDB persistence.
     */
    public smartMeterModel() {
    }

    /**
     * Constructs a new smartMeterModel with the specified details.
     *
     * @param providerId the unique identifier for the provider
     * @param currentConsumption the current power consumption in kWh
     * @param readingTimestamp the timestamp of the reading
     * @param alertFlag whether an alert is active
     * @param automatedEntryMethod whether the reading was automated
     */
    public smartMeterModel(String providerId, double currentConsumption, LocalDateTime readingTimestamp, boolean alertFlag, boolean automatedEntryMethod) {
        this.providerId = providerId;
        this.currentConsumption = currentConsumption;
        this.readingTimestamp = readingTimestamp;
        this.alertFlag = alertFlag;
        this.automatedEntryMethod = automatedEntryMethod;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public Double getCurrentConsumption() {
        return currentConsumption;
    }

    public void setCurrentConsumption(Double currentConsumption) {
        this.currentConsumption = currentConsumption;
    }

    public LocalDateTime getReadingTimestamp() {
        return readingTimestamp;
    }

    public void setReadingTimestamp(LocalDateTime readingTimestamp) {
        this.readingTimestamp = readingTimestamp;
    }

    public boolean isAutomatedEntryMethod() {
        return automatedEntryMethod;
    }

    public void setAutomatedEntryMethod(boolean automatedEntryMethod) {
        this.automatedEntryMethod = automatedEntryMethod;
    }

    public boolean isAlertFlag() {
        return alertFlag;
    }

    public void setAlertFlag(boolean alertFlag) {
        this.alertFlag = alertFlag;
    }

    @Override
    public String toString() {
        return "smartMeterModel{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", providerId='" + providerId + '\'' +
                ", currentConsumption=" + currentConsumption +
                ", readingTimestamp=" + readingTimestamp +
                ", automatedEntryMethod=" + automatedEntryMethod +
                ", alertFlag=" + alertFlag +
                '}';
    }
}
