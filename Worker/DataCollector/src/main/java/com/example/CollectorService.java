package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;
public class CollectorService extends BaseService{

    private final String id;

    public CollectorService(String inDestination, String outDestination, String brokerUrl) {
        super(inDestination, outDestination, brokerUrl);

        this.id = UUID.randomUUID().toString();
        System.out.println("Dispatch Worker (" + this.id + ") started...");
    }

    @Override
    protected String executeInternal(String input) {
        String[] inputs = input.split("[#]", 0);
        String customerId = inputs[0];
        String[] row = inputs[1].split("[|]", 0);
        String returnVal =customerId+"#0|";
        System.out.println("For customer:"+customerId);

        for (String myRow: row) {
            System.out.println("_________________________");
            String[] columns = myRow.split("@", 0);
            for (int i = 0; i < columns.length; i++) {
                System.out.println(columns[i]);
            }
            System.out.println("jdbc:postgresql://" + columns[1] + "/stationdb");

            try(Connection connection = DriverManager.getConnection("jdbc:postgresql://"+columns[1]+"/stationdb", "postgres", "postgres")){
                System.out.println("Connected to "+columns[1]);
                String sql = "SELECT * FROM charge where customer_id =?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1,Integer.valueOf(customerId));
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {

                    returnVal = returnVal +resultSet.getString("kwh")+"|";
                    System.out.println(resultSet.getString("kwh"));


                }

            }catch (Exception e) {
                returnVal = customerId+"#0";
            }

        }
        System.out.println(returnVal);

        return returnVal;
    }

}
