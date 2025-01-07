package com.example.electricalprovider.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * Model class representing a smart meter.
 * This class is mapped to the "smartMeterData" collection in MongoDB.
 */
@Document(value="smartMeterData")
public class smartMeterModel {

    @Id
    private String id;
    @Field("Customer ID")
    private String customerId;
    @Field("Provider ID")
    private String providerId;

    @Field("currentConsumption")
    private Double currentConsumption=null; //in kwh

    @Field("readingTimestamp")
    private LocalDateTime readingTimestamp=null;
    @Field("automatedEntryMethod")
    private boolean automatedEntryMethod=false;

    @Field("alertFlag")
    private boolean alertFlag=false;  // true if there's an alert

    /**
     * Default constructor.
     */
    public smartMeterModel() {
    }

    /* Parameterized constructor.
     *
     * @param providerId the id of the provider
     * @param currentConsumption the current consumption
     * @param readingTimestamp the reading timestamp
     * @param alertFlag the alert flag
     * @param AutomatedEntryMethod the automated entry method
     */
    public smartMeterModel(String providerId, double currentConsumption, LocalDateTime readingTimestamp, boolean alertFlag, boolean AutomatedEntryMethod) {
        this.providerId=providerId;
        this.currentConsumption = currentConsumption;
        this.readingTimestamp = readingTimestamp;
        this.alertFlag = alertFlag;
        this.automatedEntryMethod= AutomatedEntryMethod;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getCurrentConsumption() {
        return currentConsumption;
    }

    public void setCurrentConsumption(double currentConsumption) {
        this.currentConsumption = currentConsumption;
    }


    public LocalDateTime getReadingTimestamp() {
        return readingTimestamp;
    }

    public void setReadingTimestamp(LocalDateTime readingTimestamp) {
        this.readingTimestamp = readingTimestamp;
    }

    public boolean isAlertFlag() {
        return alertFlag;
    }

    public void setAlertFlag(boolean alertFlag) {
        this.alertFlag = alertFlag;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setCurrentConsumption(Double currentConsumption) {
        this.currentConsumption = currentConsumption;
    }

    public boolean isAutomatedEntryMethod() {
        return automatedEntryMethod;
    }

    public void setAutomatedEntryMethod(boolean automatedEntryMethod) {
        automatedEntryMethod = automatedEntryMethod;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
