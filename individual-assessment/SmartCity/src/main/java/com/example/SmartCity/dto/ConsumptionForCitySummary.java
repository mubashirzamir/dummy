package com.example.SmartCity.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ConsumptionForCitySummary {
    private Long totalConsumption;
    private Double averageConsumption;

    public ConsumptionForCitySummary(Long totalConsumption, Double averageConsumption) {
        this.totalConsumption = totalConsumption;
        this.averageConsumption = averageConsumption;
    }

}
