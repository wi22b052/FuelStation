package com.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class InvoiceController {

    private static final String API = "http://localhost:8080";

    @FXML
    private Label welcomeText;

    @FXML
    private TextField customerId;

    private String finalResponse="";

    @FXML
    protected void onGetInvoice() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(API + "/invoices/" + URLEncoder.encode(customerId.getText(), StandardCharsets.UTF_8.toString())))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        welcomeText.setText("Request sent for customer: "+customerId.getText());

        for (int i = 0; i<7;i++){
            TimeUnit.SECONDS.sleep(2);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(URI.create(API+"/invoices/file/"+URLEncoder.encode(customerId.getText(), StandardCharsets.UTF_8.toString())))
                    .build();

            HttpResponse<String> getResponse =
                    client.send(getRequest, HttpResponse.BodyHandlers.ofString());

            System.out.println(getResponse.body());
            finalResponse = getResponse.body();

        }

        welcomeText.setText("File regarding customer_id: "+ customerId.getText()+" "+finalResponse);


    }
}