package com.example.citizen.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for Smart Meter data.
 *
 * This class represents the data model for transferring smart meter readings
 * between services or clients. It includes details about the current consumption,
 * timestamp of the reading, whether an alert is triggered, and whether the reading
 * was automated.
 */
public class SmartMeterData {

    /**
     * The current power consumption in kilowatt-hours (kWh).
     */
    private double currentConsumption;

    /**
     * The timestamp when the reading was recorded.
     */
    private LocalDateTime readingTimestamp;

    /**
     * Flag indicating whether there is an alert for the current reading.
     * True if an alert is active; false otherwise.
     */
    private boolean alertFlag;

    /**
     * Flag indicating whether the reading was recorded automatically.
     * True if automated; false if manually entered.
     */
    private boolean automatedEntryMethod;

    /**
     * Default constructor.
     */
    public SmartMeterData() {
    }

    /**
     * Parameterized constructor to initialize smart meter data.
     *
     * @param currentConsumption the current power consumption in kWh
     * @param readingTimestamp the timestamp of the reading
     * @param alertFlag whether an alert is triggered
     * @param automatedEntryMethod whether the entry was automated
     */
    public SmartMeterData(double currentConsumption, LocalDateTime readingTimestamp, boolean alertFlag, boolean automatedEntryMethod) {
        this.currentConsumption = currentConsumption;
        this.readingTimestamp = readingTimestamp;
        this.alertFlag = alertFlag;
        this.automatedEntryMethod = automatedEntryMethod;
    }
    //Setter and getters
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

    public boolean isAutomatedEntryMethod() {
        return automatedEntryMethod;
    }

    public void setAutomatedEntryMethod(boolean automatedEntryMethod) {
        this.automatedEntryMethod = automatedEntryMethod;
    }
}