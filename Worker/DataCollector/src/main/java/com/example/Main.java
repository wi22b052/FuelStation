package com.example;



import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Main {
    private final static String BROKER_URL = "localhost";

    public static void main(String[] args) throws IOException, TimeoutException {
        CollectorService collectorService = new CollectorService("collectorQueue","recieverQueue",BROKER_URL);
        collectorService.run();
    }
}