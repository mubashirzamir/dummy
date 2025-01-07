package com.example.electricalprovider.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for summarising providers smart meter.
 * This class is used to transfer data between processes.
 */
public class ProviderSmartMeterSummary {


    private String ProviderId;

    private Double TotalMonthlyConsumption;

    private Double DailyAverageConsumption;

    private Double AverageConsumptionPerCitizen;

    private Double PeakHourlyConsumption;

    private Integer CitizenCount;

    private LocalDateTime Date;
    /**
     * Default constructor.
     */
    public ProviderSmartMeterSummary() {
    }

    /**
     * Parameterized constructor.
     *
     * @param providerId the id of the provider
     * @param totalMonthlyConsumption the total monthly consumption
     * @param dailyAverageConsumption the daily average consumption
     * @param averageConsumptionPerCitizen the average consumption per citizen
     * @param peakHourlyConsumption the peak hourly consumption
     * @param citizenCount the number of citizens
     * @param date the date of the summary
     */
    public ProviderSmartMeterSummary(String providerId, Double totalMonthlyConsumption, Double dailyAverageConsumption, Double averageConsumptionPerCitizen, Double peakHourlyConsumption, Integer citizenCount,LocalDateTime date) {
        ProviderId = providerId;
        TotalMonthlyConsumption = totalMonthlyConsumption;
        DailyAverageConsumption = dailyAverageConsumption;
        AverageConsumptionPerCitizen = averageConsumptionPerCitizen;
        PeakHourlyConsumption = peakHourlyConsumption;
        CitizenCount = citizenCount;
        Date=date;
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

    public Double getAverageConsumptionPerCitizen() {
        return AverageConsumptionPerCitizen;
    }

    public void setAverageConsumptionPerCitizen(Double averageConsumptionPerCitizen) {
        AverageConsumptionPerCitizen = averageConsumptionPerCitizen;
    }

    public Double getPeakHourlyConsumption() {
        return PeakHourlyConsumption;
    }

    public void setPeakHourlyConsumption(Double peakHourlyConsumption) {
        PeakHourlyConsumption = peakHourlyConsumption;
    }

    public Integer getCitizenCount() {
        return CitizenCount;
    }

    public void setCitizenCount(Integer citizenCount) {
        CitizenCount = citizenCount;
    }

    public LocalDateTime getDate() {
        return Date;
    }
    public void setDate(LocalDateTime date) {
        Date = date;
    }


    public String getProviderId() {
        return ProviderId;
    }

    public void setProviderId(String providerId) {
        this.ProviderId = providerId;
    }
}
