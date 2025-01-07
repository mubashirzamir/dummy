package com.example.SmartCity.controller;

import com.example.SmartCity.model.ElectricalProviderConsumptionSummary;
import com.example.SmartCity.service.SmartCityService;
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
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SmartCityController.class)
class SmartCityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SmartCityService smartCityService;

    @Test
    void testSaveElectricalProviderSummary_Success() throws Exception {
        String providerId = "12345";

        ElectricalProviderConsumptionSummary summary = new ElectricalProviderConsumptionSummary();
        summary.setProviderId(providerId);
        summary.setTotalMonthlyConsumption(1000.0);
        summary.setDailyAverageConsumption(33.33);
            summary.setDate(new Date());

        Mockito.when(smartCityService.fetchAndSaveElectricalProviderSummary(providerId)).thenReturn(summary);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/smartCity/electricalProvider/{providerId}/summary", providerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.providerId").value(providerId))
                .andExpect(jsonPath("$.totalMonthlyConsumption").value(1000.0))
                .andExpect(jsonPath("$.dailyAverageConsumption").value(33.33));
    }

    @Test
    void testSaveElectricalProviderSummary_BadRequest() throws Exception {
        String providerId = "12345";
        String errorMessage = "Data already exists for the current month. Please call Put instead.";

        Mockito.when(smartCityService.fetchAndSaveElectricalProviderSummary(providerId))
                .thenThrow(new IllegalArgumentException(errorMessage));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/smartCity/electricalProvider/{providerId}/summary", providerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(errorMessage));
    }

    @Test
    void testViewIndividualProviderSummary_Success() throws Exception {
        String providerId = new ObjectId().toString();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -1);
        List<ElectricalProviderConsumptionSummary> summaries = Arrays.asList(
                new ElectricalProviderConsumptionSummary(providerId, 1000.0, 33.33, 150.0, 10.0, 5, new Date()),
                new ElectricalProviderConsumptionSummary(providerId, 800.0, 26.67, 130.0, 8.0, 4, cal.getTime())
        );

        Mockito.when(smartCityService.viewIndividualProviderSummary(providerId)).thenReturn(summaries);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/smartCity/electricalProvider/{providerId}/summary", providerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].providerId").value(providerId))
                .andExpect(jsonPath("$[0].totalMonthlyConsumption").value(1000.0))
                .andExpect(jsonPath("$[1].totalMonthlyConsumption").value(800.0));
    }

    @Test
    void testViewIndividualProviderSummary_BadRequest() throws Exception {
        String providerId = "12345";
        String errorMessage = "No data found for provider ID: " + providerId;

        Mockito.when(smartCityService.viewIndividualProviderSummary(providerId))
                .thenThrow(new IllegalArgumentException(errorMessage));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/smartCity/electricalProvider/{providerId}/summary", providerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(errorMessage));
    }

    @Test
    void testUpdateElectricalProviderSummary_Success() throws Exception {
        String providerId = "12345";

        ElectricalProviderConsumptionSummary summary = new ElectricalProviderConsumptionSummary();
        summary.setProviderId(providerId);
        summary.setTotalMonthlyConsumption(1200.0);
        summary.setDailyAverageConsumption(40.0);
        summary.setDate(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));

        Mockito.when(smartCityService.updateElectricalProviderSummary(providerId)).thenReturn(summary);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/smartCity/electricalProvider/{providerId}/summary", providerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.providerId").value(providerId))
                .andExpect(jsonPath("$.totalMonthlyConsumption").value(1200.0))
                .andExpect(jsonPath("$.dailyAverageConsumption").value(40.0))
                .andExpect(jsonPath("$.date").exists());
    }

    @Test
    void testUpdateElectricalProviderSummary_BadRequest() throws Exception {
        String providerId = "12345";
        String errorMessage = "No data found for provider ID: " + providerId;

        Mockito.when(smartCityService.updateElectricalProviderSummary(providerId))
                .thenThrow(new IllegalArgumentException(errorMessage));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/smartCity/electricalProvider/{providerId}/summary", providerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(errorMessage));
    }
}
