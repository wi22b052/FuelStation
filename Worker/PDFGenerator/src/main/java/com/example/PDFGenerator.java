package com.example;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;
public class PDFGenerator extends BaseService{

    private final String id;

    public PDFGenerator(String inDestination, String brokerUrl) {
        super(inDestination, brokerUrl);

        this.id = UUID.randomUUID().toString();
        System.out.println("Dispatch Worker (" + this.id + ") started...");
    }

    @Override
    protected String executeInternal(String input) {
        String[] inputs = input.split("[#]", 0);
        String customerId = inputs[0];

        String kwhValue = inputs[1];
        System.out.println("Hello customer:"+customerId);
        System.out.println("kwh:"+kwhValue);
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:30001/customerdb", "postgres", "postgres")) {

                String sql = "SELECT * FROM customer where id =?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, Integer.valueOf(customerId));
                ResultSet resultSet = preparedStatement.executeQuery();
                Double kosten = (double) Math.round(Double.valueOf(kwhValue) * 0.3 * 100) / 100;

                resultSet.next();
                System.out.println(resultSet.getString("first_name"));

                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream("Invoice_Customer_" + customerId + ".pdf"));
                document.open();
                Font font = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.BLACK);
                Chunk hello = new Chunk("Hello " + resultSet.getString("first_name") + " " + resultSet.getString("last_name") + " auf der nächsten Seite finden sie eine Tabelle die ihre Kosten aufschlüsseln:", font);
                PdfPTable pdfPTable = new PdfPTable(3);
                pdfPTable.addCell("KWh");
                pdfPTable.addCell("Kosten Pro KWh");
                pdfPTable.addCell("Gesamtkosten");
                pdfPTable.addCell(kwhValue);
                pdfPTable.addCell("0.3€");
                pdfPTable.addCell(kosten.toString() + "€");

                document.add(hello);
                document.newPage();
                document.add(pdfPTable);
                document.close();

            } catch (Exception e) {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream("Invoice_Customer_" + customerId + ".pdf"));
                document.open();
                Font font = FontFactory.getFont(FontFactory.COURIER, 20, BaseColor.BLACK);
                document.add(new Chunk("KEINE DATEN GEFUNDEN ZU CUSTOMER_ID: " + customerId, font));
                document.close();
            }
        }
        catch (Exception q){
            throw new IllegalStateException("ERROR!", q);
        }


        //throw new IllegalStateException("Cannot connect the database!", e)
        return "finished";
    }

}
