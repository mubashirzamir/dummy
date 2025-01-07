package com.example.citizen.publisher;

import com.example.citizen.model.smartMeterModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service for publishing smart meter data to RabbitMQ.
 *
 * This class is responsible for sending smart meter readings to the configured
 * RabbitMQ exchange using a specified routing key. It uses Spring's {@link RabbitTemplate}
 * to interact with the RabbitMQ broker.
 * Its purpose is to publish smart meter data to the electrical provider microservice.
 *
 * Responsibilities:
 *
 *  Publishes smart meter data to RabbitMQ.
 *  Handles exceptions during the publishing process.
 *
 */
@Service
public class SmartMeterPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Autowired
    public SmartMeterPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishSmartMeterData(smartMeterModel data) {
        // Routing key pattern: "electricalProvider.<providerId>"
        String routingKey = "electricalProvider." + data.getProviderId();

        // Publish data to RabbitMQ
        rabbitTemplate.convertAndSend(exchangeName, routingKey, data);
        System.out.println("Published smart meter data with routing key: " + routingKey);
    }
}


