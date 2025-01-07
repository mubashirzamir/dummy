package com.example.SmartCity.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
public class MonthlyAverageByProviderSummary {
    @Field("ProviderId")  // Maps to the `providerId` field in the MongoDB projection
    private String providerId;

    @Field("month")  // Maps to the `month` field in the MongoDB projection
    private Integer month;

    @Field("averageConsumption")  // Maps to the `averageConsumption` field in the MongoDB projection
    private Double averageConsumption;

    public MonthlyAverageByProviderSummary(String providerId, Integer month, Double averageConsumption) {
        this.providerId = providerId;
        this.month = month;
        this.averageConsumption = averageConsumption;
    }

}
