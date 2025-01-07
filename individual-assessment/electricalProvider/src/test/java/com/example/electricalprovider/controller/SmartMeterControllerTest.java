package com.example.electricalprovider.controller;

import com.example.electricalprovider.dto.ProviderSmartMeterSummary;
import com.example.electricalprovider.dto.UserSmartMeterReport;
import com.example.electricalprovider.service.SmartMeterService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SmartMeterController.class)
class SmartMeterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SmartMeterService smartMeterService;

    @Test
    void testRegisterSmartMeter_Success() throws Exception {
        String providerId = new ObjectId().toHexString();
        String userId = new ObjectId().toHexString();
        String successMessage = "Smart meter registered successfully";

        Mockito.when(smartMeterService.postRegistrationSmartMeter(providerId, userId))
                .thenReturn(successMessage);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/electricalProvider/{providerId}/smartMeter/register/users/{id}", providerId, userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(successMessage));
    }

    @Test
    void testRegisterSmartMeter_BadRequest() throws Exception {
        String providerId = new ObjectId().toHexString();
        String userId = new ObjectId().toHexString();
        String errorMessage = "Error while registering smart meter";

        Mockito.when(smartMeterService.postRegistrationSmartMeter(providerId, userId))
                .thenThrow(new IllegalArgumentException(errorMessage));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/electricalProvider/{providerId}/smartMeter/register/users/{id}", providerId, userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(errorMessage));
    }

    @Test
    void testGetSummarySmartMeterDataByUser_Success() throws Exception {
        String providerId = new ObjectId().toHexString();
        String customerId = new ObjectId().toHexString();
        UserSmartMeterReport report = new UserSmartMeterReport();
        report.setCustomerId(customerId);
        report.setTotalMonthlyConsumption(500.0);
        report.setDailyAverageConsumption(16.67);
        report.setPeakHourlyConsumption(50.0);
        report.setNumberOfReadingsRecorded(30);
        report.setHasManualEntry(false);

        Mockito.when(smartMeterService.summarySmartMeterDataByUser(providerId, customerId))
                .thenReturn(report);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/electricalProvider/{providerId}/smartMeter/users/{customerId}/summary", providerId, customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(customerId))
                .andExpect(jsonPath("$.totalMonthlyConsumption").value(500.0))
                .andExpect(jsonPath("$.dailyAverageConsumption").value(16.67))
                .andExpect(jsonPath("$.peakHourlyConsumption").value(50.0))
                .andExpect(jsonPath("$.numberOfReadingsRecorded").value(30))
                .andExpect(jsonPath("$.hasManualEntry").value(false));
    }

    @Test
    void testGetSummarySmartMeterDataByUser_BadRequest() throws Exception {
        String providerId = new ObjectId().toHexString();
        String customerId = new ObjectId().toHexString();
        String errorMessage = "No smart meter data found for the given user.";

        Mockito.when(smartMeterService.summarySmartMeterDataByUser(providerId, customerId))
                .thenThrow(new IllegalArgumentException(errorMessage));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/electricalProvider/{providerId}/smartMeter/users/{customerId}/summary", providerId, customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(errorMessage));
    }

    @Test
    void testGetSummaryElectricalProviderSmartMeterData_Success() throws Exception {
        String providerId = new ObjectId().toHexString();
        ProviderSmartMeterSummary summary = new ProviderSmartMeterSummary();
        summary.setProviderId(providerId);
        summary.setTotalMonthlyConsumption(1500.0);
        summary.setDailyAverageConsumption(50.0);
        summary.setAverageConsumptionPerCitizen(100.0);
        summary.setPeakHourlyConsumption(200.0);
        summary.setCitizenCount(15);
        summary.setDate(LocalDateTime.now());

        Mockito.when(smartMeterService.summarySmartMeterDataByProvider(providerId))
                .thenReturn(summary);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/electricalProvider/{providerId}/smartMeter/details", providerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.providerId").value(providerId))
                .andExpect(jsonPath("$.totalMonthlyConsumption").value(1500.0))
                .andExpect(jsonPath("$.dailyAverageConsumption").value(50.0))
                .andExpect(jsonPath("$.averageConsumptionPerCitizen").value(100.0))
                .andExpect(jsonPath("$.peakHourlyConsumption").value(200.0))
                .andExpect(jsonPath("$.citizenCount").value(15));
    }

    @Test
    void testGetSummaryElectricalProviderSmartMeterData_BadRequest() throws Exception {
        String providerId = new ObjectId().toHexString();
        String errorMessage = "No smart meter data found for the given provider.";

        Mockito.when(smartMeterService.summarySmartMeterDataByProvider(providerId))
                .thenThrow(new IllegalArgumentException(errorMessage));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/electricalProvider/{providerId}/smartMeter/details", providerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(errorMessage));
    }
}
