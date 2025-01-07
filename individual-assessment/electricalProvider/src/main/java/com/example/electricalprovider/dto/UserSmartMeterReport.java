package com.example.electricalprovider.dto;

/**
 * Data Transfer Object (DTO) for summarising users smart meter data.
 * This class is used to transfer data between processes.
 */
public class UserSmartMeterReport {

    private String CustomerId;

    private Double TotalMonthlyConsumption;

    private Double DailyAverageConsumption;

    private Double PeakHourlyConsumption;

    private Integer NumberOfReadingsRecorded;

    private boolean HasManualEntry;

    /**
     * Default constructor.
     */
    public UserSmartMeterReport() {
    }

    /**
     * Parameterized constructor.
     *
     * @param customerId the id of the customer
     * @param totalMonthlyConsumption the total monthly consumption
     * @param dailyAverageConsumption the daily average consumption
     * @param peakHourlyConsumption the peak hourly consumption
     * @param numberOfReadingsRecorded the number of readings recorded
     * @param hasManualEntry the manual entry status
     */
    public UserSmartMeterReport(String customerId, Double totalMonthlyConsumption, Double dailyAverageConsumption, Double peakHourlyConsumption, Integer numberOfReadingsRecorded, boolean hasManualEntry) {
        CustomerId = customerId;
        TotalMonthlyConsumption = totalMonthlyConsumption;
        DailyAverageConsumption = dailyAverageConsumption;
        PeakHourlyConsumption = peakHourlyConsumption;
        NumberOfReadingsRecorded = numberOfReadingsRecorded;
        HasManualEntry=hasManualEntry;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public Double getTotalMonthlyConsumption() {
        return TotalMonthlyConsumption;
    }

    public void setTotalMonthlyConsumption(Double totalMonthlyConsumption) {
        TotalMonthlyConsumption = totalMonthlyConsumption;
    }

    public Double getDailyAverageConsumption() {
        return DailyAverageConsumption;
    }

    public void setDailyAverageConsumption(Double dailyAverageConsumption) {
        DailyAverageConsumption = dailyAverageConsumption;
    }

    public Double getPeakHourlyConsumption() {
        return PeakHourlyConsumption;
    }

    public void setPeakHourlyConsumption(Double peakHourlyConsumption) {
        PeakHourlyConsumption = peakHourlyConsumption;
    }

    public Integer getNumberOfReadingsRecorded() {
        return NumberOfReadingsRecorded;
    }

    public void setNumberOfReadingsRecorded(Integer numberOfReadingsRecorded) {
        NumberOfReadingsRecorded = numberOfReadingsRecorded;
    }

    public boolean isHasManualEntry() {
        return HasManualEntry;
    }

    public void setHasManualEntry(boolean hasManualEntry) {
        HasManualEntry = hasManualEntry;
    }
}
