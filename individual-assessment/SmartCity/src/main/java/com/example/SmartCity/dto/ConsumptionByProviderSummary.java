package com.example.SmartCity.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
public class ConsumptionByProviderSummary {
    @Field("ProviderId")  // Maps to the `providerId` field in the MongoDB projection
    private String providerId;

    @Field("totalConsumption")  // Maps to the `totalConsumption` field in the MongoDB projection
    private Double totalConsumption;

    @Field("averageConsumption")  // Maps to the `averageConsumption` field in the MongoDB projection
    private Double averageConsumption;

    public ConsumptionByProviderSummary(String providerId, Double totalConsumption, Double averageConsumption) {
        this.providerId = providerId;
        this.totalConsumption = totalConsumption;
        this.averageConsumption = averageConsumption;
    }

}

