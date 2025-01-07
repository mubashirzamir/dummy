package com.example.electricalprovider.controller;

import com.example.electricalprovider.dto.ElectricalProviderUpdateDTO;
import com.example.electricalprovider.models.ElectricalProviderModel;
import com.example.electricalprovider.service.ElectricalProviderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ElectricalProviderInfoController.class)
class ElectricalProviderInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ElectricalProviderService electricalProviderService;

    @Test
    void testRegisterProvider_Success() throws Exception {
        String requestBody = """
                {
                    "companyName": "Data1",
                    "companyAddress": "123 data Lane",
                    "companyPhoneNumber": "1234567890",
                    "companyEmail": "contact@data1.com"
                }
                """;

        Mockito.doNothing().when(electricalProviderService).registerProvider(Mockito.any(ElectricalProviderModel.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/electricalProvider/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("Provider registered successfully"));
    }

    @Test
    void testRegisterProvider_MissingField_BadRequest() throws Exception {
        String requestBody = """
                {
                    "companyAddress": "123 data Lane",
                    "companyPhoneNumber": "1234567890",
                    "companyEmail": "contact@data1.com"
                }
                """;

        Mockito.doThrow(new IllegalArgumentException("Company name is required"))
                .when(electricalProviderService).registerProvider(Mockito.any(ElectricalProviderModel.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/electricalProvider/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Company name is required"));
    }

    @Test
    void testGetProvider_Success() throws Exception {
        String providerId = "12345";
        ElectricalProviderModel providerData = new ElectricalProviderModel();
        providerData.setId(providerId);
        providerData.setCompanyName("Data1");
        providerData.setCompanyAddress("123 data Lane");
        providerData.setCompanyPhoneNumber("1234567890");
        providerData.setCompanyEmail("contact@data1.com");

        ElectricalProviderUpdateDTO publishdata= new ElectricalProviderUpdateDTO();
        publishdata.setCompanyName(providerData.getCompanyName());
        publishdata.setCompanyAddress(providerData.getCompanyAddress());
        publishdata.setCompanyPhoneNumber(providerData.getCompanyPhoneNumber());
        publishdata.setCompanyEmail(providerData.getCompanyEmail());

        Mockito.when(electricalProviderService.getProvider(providerId)).thenReturn(publishdata);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/electricalProvider/provider/{id}", providerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                    {
                        "companyName": "Data1",
                        "companyAddress": "123 data Lane",
                        "companyPhoneNumber": "1234567890",
                        "companyEmail": "contact@data1.com"
                    }
                    """));
    }

    @Test
    void testGetProvider_NotFound() throws Exception {
        String providerId = "12345";
        String errorMessage = "Citizen not found with id: " + providerId;

        // Mock the service to throw an IllegalArgumentException
        Mockito.doThrow(new IllegalArgumentException(errorMessage))
                .when(electricalProviderService).getProvider(providerId);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/electricalProvider/provider/{providerId}", providerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error reason: " + errorMessage));
    }

    @Test
    void testGetProvider_InternalServerError() throws Exception {
        String providerId = "12345";
        String errorMessage = "Unexpected server error";

        // Mock the service to throw a generic Exception
        Mockito.doThrow(new RuntimeException(errorMessage))
                .when(electricalProviderService).getProvider(providerId);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/electricalProvider/provider/{providerId}", providerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error reason: " + errorMessage));
    }

    @Test
    void testUpdateProvider_Success() throws Exception {
        String providerId = "12345";
        String requestBody = """
                {
                    "companyName": "Data1",
                    "companyAddress": "123 data Lane",
                    "companyPhoneNumber": "1234567890",
                    "companyEmail": "contact@data1.com"
                }
                """;

        Mockito.doNothing().when(electricalProviderService).updateProvider(Mockito.eq(providerId), Mockito.any());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/electricalProvider/provider/{id}", providerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("Provider updated successfully"));
    }

    @Test
    void testUpdateProvider_NotFound() throws Exception {
        String providerId = "12345";
        String requestBody = """
                {
                    "name": "Updated data"
                }
                """;

        Mockito.doThrow(new IllegalArgumentException("Provider with ID " + providerId + " not found"))
                .when(electricalProviderService).updateProvider(Mockito.eq(providerId), Mockito.any());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/electricalProvider/provider/{id}", providerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error reason: Provider with ID 12345 not found"));
    }

    @Test
    void testDeleteProvider_Success() throws Exception {
        String providerId = "12345";

        Mockito.doNothing().when(electricalProviderService).deleteProvider(providerId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/electricalProvider/provider/{id}", providerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Provider deleted successfully"));
    }

    @Test
    void testDeleteProvider_NotFound() throws Exception {
        String providerId = "12345";

        Mockito.doThrow(new IllegalArgumentException("Provider not found"))
                .when(electricalProviderService).deleteProvider(providerId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/electricalProvider/provider/{id}", providerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error reason: Provider not found"));
    }
}
