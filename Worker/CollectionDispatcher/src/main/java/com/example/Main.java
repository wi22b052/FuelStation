package com.example;



import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Main {
    private final static String BROKER_URL = "localhost";

    public static void main(String[] args) throws IOException, TimeoutException {
        DistpatchService distpatchService = new DistpatchService("dispatcherQueue","collectorQueue",BROKER_URL);
        distpatchService.run();
    }
}