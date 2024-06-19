package com.example;



import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Main {
    private final static String BROKER_URL = "localhost";

    public static void main(String[] args) throws IOException, TimeoutException {
        RecieverService recieverService = new RecieverService("recieverQueue","generateQueue",BROKER_URL);
        recieverService.run();
    }
}