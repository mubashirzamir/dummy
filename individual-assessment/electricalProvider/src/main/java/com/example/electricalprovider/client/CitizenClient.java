package com.example.electricalprovider.client;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface CitizenClient {

    /**
     * Registers a smart meter for a citizen via the Citizen Microservice.
     * <p>
     * This method performs a POST request to the endpoint:
     * {@code /providers/{providerId}/users/{id}/smartMeter}.
     * </p>
     *
     * @param providerId the unique identifier for the electricity provider.
     *                   This is a required parameter.
     * @param id the unique identifier for the citizen for whom the smart meter
     *           is being registered. This is a required parameter.
     * @return a {@code String} response from the Citizen Microservice,
     *         indicating the success or failure of the registration.
     */
    @PostExchange("/providers/{providerId}/users/{id}/smartMeter")
    String registerSmartMeter(@PathVariable("providerId") String providerId, @PathVariable("id") String id);

}

