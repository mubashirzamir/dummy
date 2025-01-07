package com.example.SmartCity.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MonthlyAverageForCitySummary {
    private Integer month;
    private Double averageConsumption;

    public MonthlyAverageForCitySummary(Integer month, Double averageConsumption) {
        this.month = month;
        this.averageConsumption = averageConsumption;
    }

}

