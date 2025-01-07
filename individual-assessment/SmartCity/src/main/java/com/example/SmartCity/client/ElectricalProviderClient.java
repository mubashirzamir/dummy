package com.example.SmartCity.client;

import com.example.SmartCity.dto.ElectricalProviderModel;
import com.example.SmartCity.model.ElectricalProviderConsumptionSummary;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "electricalProvider", url = "${apigateway.url}")
public interface ElectricalProviderClient {
    @GetMapping("/api/electricalProvider/{providerId}/smartMeter/details")
    ElectricalProviderConsumptionSummary getSummaryElectricalProviderSmartMeterData(@PathVariable("providerId") String providerId);

    @GetMapping("/api/electricalProvider/{providerId}/smartMeter/allDetails")
    List<ElectricalProviderConsumptionSummary> getAllSummaryElectricalProviderSmartMeterData(@PathVariable("providerId") String providerId);

    @GetMapping("/api/electricalProvider/providerInfo")
    List<ElectricalProviderModel> getAllElectricalProviderInfo();
}

