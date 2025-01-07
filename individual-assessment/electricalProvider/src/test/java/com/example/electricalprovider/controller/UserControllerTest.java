package com.example.electricalprovider.controller;


import com.example.electricalprovider.models.UserModel;
import com.example.electricalprovider.service.UserService;
import org.bson.types.ObjectId;
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

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
 class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    public void testCreateCitizen_Success() throws Exception {
        String providerId = new ObjectId().toString();
        String requestBody = """
                {
                    "Name": "John Doe",
                    "email": "john.doe@example.com",
                    "phone": "1234567890",
                    "City": "New York",
                    "State": "NY",
                    "Country": "USA",
                    "PostalCode": "10001",
                    "address": "123 Main St"
                }
                """;
        String successMessage = "Citizen created successfully with id: " + new ObjectId().toString();

        Mockito.when(userService.addCitizen(Mockito.eq(providerId), Mockito.any(UserModel.class)))
                .thenReturn(successMessage);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/electricalProvider/{providerId}/citizen", providerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(successMessage));
    }

    @Test
    public void testCreateCitizen_InvalidProviderId_BadRequest() throws Exception {
        String invalidProviderId = "invalid-id";
        String requestBody = """
                {
                    "Name": "John Doe",
                    "email": "john.doe@example.com",
                    "phone": "1234567890",
                    "City": "New York",
                    "State": "NY",
                    "Country": "USA",
                    "PostalCode": "10001",
                    "address": "123 Main St"
                }
                """;
        String errorMessage = "Invalid provider ID";

        Mockito.when(userService.addCitizen(Mockito.eq(invalidProviderId), Mockito.any(UserModel.class)))
                .thenThrow(new IllegalArgumentException(errorMessage));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/electricalProvider/{providerId}/citizen", invalidProviderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error creating Citizen: " + errorMessage));
    }

    @Test
    public void testCreateCitizen_InternalServerError() throws Exception {
        String providerId = new ObjectId().toString();
        String requestBody = """
                {
                    "Name": "John Doe",
                    "email": "john.doe@example.com",
                    "phone": "1234567890",
                    "City": "New York",
                    "State": "NY",
                    "Country": "USA",
                    "PostalCode": "10001",
                    "address": "123 Main St"
                }
                """;

        Mockito.when(userService.addCitizen(Mockito.eq(providerId), Mockito.any(UserModel.class)))
                .thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/electricalProvider/{providerId}/citizen", providerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error creating Citizen: Unexpected error"));
    }
    @Test
    void testGetAllCitizens_Success() throws Exception {
        String providerId = new ObjectId().toString();
        List<UserModel> citizens = Arrays.asList(
                new UserModel(providerId,"John Doe", 1,  "john@example.com", "1234567890", "New York", "NY", "USA", "10001", "123 Main St"),
                new UserModel(providerId, "Jane Doe",2,  "jane@example.com", "9876543210", "Los Angeles", "CA", "USA", "90001", "456 Elm St")
        );

        Mockito.when(userService.getAllCitizens(providerId)).thenReturn(citizens);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/electricalProvider/{providerId}/citizen", providerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                [
                    {
                        "name": "John Doe",
                        "email": "john@example.com",
                        "phone": "1234567890",
                        "city": "New York",
                        "state": "NY",
                        "country": "USA",
                        "postalCode": "10001",
                        "address": "123 Main St"
                    },
                    {
                        "name": "Jane Doe",
                        "email": "jane@example.com",
                        "phone": "9876543210",
                        "city": "Los Angeles",
                        "state": "CA",
                        "country": "USA",
                        "postalCode": "90001",
                        "address": "456 Elm St"
                    }
                ]
                """));
    }

    @Test
    void testGetAllCitizens_NotFound() throws Exception {
        String providerId = new ObjectId().toString();
        Mockito.when(userService.getAllCitizens(providerId))
                .thenThrow(new IllegalArgumentException("No citizens found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/electricalProvider/{providerId}/citizen", providerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error fetching Citizens: No citizens found"));
    }

    @Test
    void testGetCitizenById_Success() throws Exception {
        String providerId = new ObjectId().toString();
        String citizenId = new ObjectId().toString();
        UserModel citizen = new UserModel(providerId,"John Doe", 1,  "john@example.com", "1234567890", "New York", "NY", "USA", "10001", "123 Main St");

        Mockito.when(userService.getCitizenById(providerId, citizenId)).thenReturn(citizen);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/electricalProvider/{providerId}/citizen/{id}", providerId, citizenId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "name": "John Doe",
                            "email": "john@example.com",
                            "phone": "1234567890",
                            "city": "New York",
                            "state": "NY",
                            "country": "USA",
                            "postalCode": "10001",
                            "address": "123 Main St"
                        }
                        """));
    }

    @Test
    void testGetCitizenById_NotFound() throws Exception {
        String providerId = new ObjectId().toString();
        String citizenId = new ObjectId().toString();
        Mockito.when(userService.getCitizenById(providerId, citizenId))
                .thenThrow(new IllegalArgumentException("Citizen not found with id: " + citizenId));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/electricalProvider/{providerId}/citizen/{id}", providerId, citizenId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error fetching Citizen: Citizen not found with id: " + citizenId));
    }
    @Test
    void testUpdateCitizenData_Success() throws Exception {
        String providerId = new ObjectId().toString();
        String citizenId = new ObjectId().toString();
        String requestBody = """
                {
                    "Name": "Updated Name",
                    "email": "updated@example.com",
                    "City": "Updated City",
                    "address": "Updated Address"
                }
                """;
        String successMessage = "Citizen updated successfully with id: " + citizenId;

        Mockito.when(userService.updateCitizenData(Mockito.eq(providerId), Mockito.eq(citizenId), Mockito.any(UserModel.class)))
                .thenReturn(successMessage);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/electricalProvider/{providerId}/citizen/{id}", providerId, citizenId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(successMessage));
    }

    @Test
    void testUpdateCitizenData_CitizenNotFound() throws Exception {
        String providerId = new ObjectId().toString();
        String citizenId = new ObjectId().toString();
        String requestBody = """
                {
                    "Name": "Updated Name"
                }
                """;
        String errorMessage = "Citizen not found with id: " + citizenId;

        Mockito.when(userService.updateCitizenData(Mockito.eq(providerId), Mockito.eq(citizenId), Mockito.any(UserModel.class)))
                .thenThrow(new IllegalArgumentException(errorMessage));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/electricalProvider/{providerId}/citizen/{id}", providerId, citizenId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error updating Citizen: " + errorMessage));
    }

    @Test
    void testDeleteCitizen_Success() throws Exception {
        String providerId = new ObjectId().toString();
        String citizenId = new ObjectId().toString();
        String successMessage = "Citizen deleted successfully with id: " + citizenId;

        Mockito.when(userService.deleteCitizen(providerId, citizenId)).thenReturn(successMessage);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/electricalProvider/{providerId}/citizen/{id}", providerId, citizenId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(successMessage));
    }

    @Test
    void testDeleteCitizen_CitizenNotFound() throws Exception {
        String providerId = new ObjectId().toString();
        String citizenId = new ObjectId().toString();
        String errorMessage = "Citizen not found with id: " + citizenId;

        Mockito.when(userService.deleteCitizen(providerId, citizenId))
                .thenThrow(new IllegalArgumentException(errorMessage));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/electricalProvider/{providerId}/citizen/{id}", providerId, citizenId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error deleting Citizen: " + errorMessage));
    }
}