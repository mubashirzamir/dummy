package com.example.apiGateway.routes;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * Configuration class for defining routes and fallback route for the API Gateway.
 */
@Configuration
public class Routes {

    /**
     * Defines the route locator bean which sets up the routes for the API Gateway.
     *
     * @param builder the RouteLocatorBuilder used to build the routes
     * @return the configured RouteLocator
     */
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("citizenService", r -> r
                        .path("/api/citizen/**")
                        .uri("http://citizen:9089"))
                .route("electricalProviderService", r -> r
                        .path("/api/electricalProvider/**")
                        .uri("http://provider:9087"))
                .route("smartCity", r -> r
                        .path("/api/smartCity/**")
                        .uri("http://city:9088"))
                .route("fallbackRoute", r -> r
                        .path("/fallba ckRoute")
                        .filters(f -> f.setPath("/fallbackRoute"))
                        .uri("forward:/fallbackRoute"))
                .build();
    }

    /**
     * Defines the fallback route function which returns a service unavailable response.
     *
     * @return the RouterFunction for the fallback route
     */
    @Bean
    public RouterFunction<ServerResponse> fallbackRoute() {
        return RouterFunctions.route(RequestPredicates.GET("/fallbackRoute"),
                request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(BodyInserters.fromValue("Service is down. Please try again later.")));
    }
}