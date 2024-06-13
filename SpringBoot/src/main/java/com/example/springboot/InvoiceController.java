package com.example.springboot;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;


@RestController
public class InvoiceController {
    private final static String brokerUrl = "localhost";

    @PostMapping({"/invoices/{id}"})
    public String getId(@PathVariable String id)
    {
        Producer.send(id,"dispatcherQueue",brokerUrl);

        System.out.println(id);
        return id;
    }

    @GetMapping({"/invoices/file/{id}"})
    public String getPDF(@PathVariable String id)
    {
        String returnMessage = "Not coming..Sorry";
        File f = new File("../Worker/PDFGenerator/Invoice_Customer_"+id+".pdf");
        if(f.exists() && !f.isDirectory()) {
            returnMessage = "IS HERE!!!!";
        }
        System.out.println(returnMessage+" "+id);
        return returnMessage;
    }

}
