package com.example.SmartCity.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Class used to store electricalproviderconsumptionsummary data
 * ElectricalProviderConsumptionSummary
 */
@Setter
@Getter
@Document(collection = "ElectricalProviderConsumptionSummary")
public class ElectricalProviderConsumptionSummary {

    @Id
    @JsonProperty("id")
    private String id;

    @Field("ProviderId")
    @JsonProperty("providerId")
    private String providerId;

    @Field("TotalMonthlyConsumption")
    @JsonProperty("totalMonthlyConsumption")
    private Double totalMonthlyConsumption;

    @Field("DailyAverageConsumption")
    @JsonProperty("dailyAverageConsumption")
    private Double dailyAverageConsumption;

    @Field("AverageConsumptionPerCitizen")
    @JsonProperty("averageConsumptionPerCitizen")
    private Double averageConsumptionPerCitizen;

    @Field("PeakHourlyConsumption")
    @JsonProperty("peakHourlyConsumption")
    private Double peakHourlyConsumption;

    @Field("CitizenCount")
    @JsonProperty("citizenCount")
    private Integer citizenCount;

    @Field("Date")
    @JsonProperty("date")
    private Date date;

    public ElectricalProviderConsumptionSummary() {
    }

    public ElectricalProviderConsumptionSummary(String providerId, Double totalMonthlyConsumption, Double dailyAverageConsumption, Double averageConsumptionPerCitizen, Double peakHourlyConsumption, Integer citizenCount, Date date) {
        this.providerId = providerId;
        this.totalMonthlyConsumption = totalMonthlyConsumption;
        this.dailyAverageConsumption = dailyAverageConsumption;
        this.averageConsumptionPerCitizen = averageConsumptionPerCitizen;
        this.peakHourlyConsumption = peakHourlyConsumption;
        this.citizenCount = citizenCount;
        this.date = date;
    }

}