package com.example.electricalprovider.consumer;

import com.example.electricalprovider.models.smartMeterModel;
import com.example.electricalprovider.service.SmartMeterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service class responsible for routing smart meter data.
 * This class is used to accept data from citizen microservice asynchronously.
 */
@Service
public class SmartMeterRouterService {

    private final SmartMeterService smartMeterService;

    @Autowired
    public SmartMeterRouterService(SmartMeterService smartMeterService) {
        this.smartMeterService = smartMeterService;
    }

    /**
     * Listens for smart meter data messages from the queue and processes them.
     *
     * @param smartMeterData the received smart meter data
     */
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void routeToProviderQueue(smartMeterModel smartMeterData) {
        System.out.println("Received smart meter data: " + smartMeterData);
        smartMeterService.processSmartMeterData(smartMeterData);
    }
}




