package com.example.electricalprovider.config;

import com.example.electricalprovider.client.CitizenClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.UUID;

/**
 * Configuration class for REST clients.
 */
@Configuration
public class RestClientConfig {

    @Value("${citizenServiceUrl}")
    private String citizenServiceUrl;

    /**
     * Creates a CitizenClient bean configured with the base URL specified in the application properties.
     *
     * @return the CitizenClient bean
     */
    @Bean
    public CitizenClient citizenClient(){
        RestClient restClient = RestClient.builder()
                .baseUrl(citizenServiceUrl)
                .build();
        var restClientAdapter = RestClientAdapter.create(restClient);
        var httpServceProxyFactory= HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return httpServceProxyFactory.createClient(CitizenClient.class);
    }

    /**
     * Creates a RestTemplate bean with a default header containing a unique instance identifier.
     *
     * @return the RestTemplate bean
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .defaultHeader("X-Instance-Identifier", UUID.randomUUID().toString())
                .build();
    }
}
