package com.example;

import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public abstract class BaseService {
    protected final String inDestination;
    protected final String brokerUrl;

    public BaseService(String inDestination, String brokerUrl) {
        this.inDestination = inDestination;
        this.brokerUrl = brokerUrl;
    }

    public void run() throws IOException, TimeoutException {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String input = new String(delivery.getBody(), StandardCharsets.UTF_8);
            String output = executeInternal(input);
        };
        Consumer.receive(inDestination, 10000, brokerUrl, deliverCallback);
    }

    protected abstract String executeInternal(String input);
}
