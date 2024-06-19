package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;
public class DistpatchService extends BaseService{

    private final String id;

    public DistpatchService(String inDestination, String outDestination, String brokerUrl) {
        super(inDestination, outDestination, brokerUrl);

        this.id = UUID.randomUUID().toString();
        System.out.println("Dispatch Worker (" + this.id + ") started...");
    }

    @Override
    public String executeInternal(String input) {
        String returnVal=input+"#";

        try(Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:30002/stationdb", "postgres", "postgres")){

            String sql = "SELECT * FROM station";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                returnVal = returnVal + resultSet.getString("id")+"@"+ resultSet.getString("db_url")+"|";

            }

        }catch (Exception e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        System.out.println(returnVal);
        return returnVal;
    }

}
