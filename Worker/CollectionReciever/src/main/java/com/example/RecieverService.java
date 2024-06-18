package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.UUID;
public class RecieverService extends BaseService{

    private final String id;

    public RecieverService(String inDestination, String outDestination, String brokerUrl) {
        super(inDestination, outDestination, brokerUrl);

        this.id = UUID.randomUUID().toString();
        System.out.println("Reciever Worker (" + this.id + ") started...");
    }

    @Override
    protected String executeInternal(String input) {
        String message;
        String[] inputs = input.split("[#]", 0);
        String customerId = inputs[0];
        System.out.println(input);
        String[] values = inputs[1].split("[|]", 0);
        Double sum = (double) 0;

        System.out.println(customerId);
        for (String myValues: values){
            System.out.println(myValues);
            sum = sum + Double.valueOf(myValues);
        }
        sum = (double) Math.round(sum*100)/100;
        message = customerId+"#"+sum.toString();
        System.out.println(message);

        return message;
    }

}
