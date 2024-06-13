package com.example.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Assert;

import java.io.File;


@SpringBootTest
class ApplicationTests {

	@Test
	void hereOrNot() {
		InvoiceController invoiceController = new InvoiceController();
		Boolean isHere = false;
		File f = new File("../Worker/PDFGenerator/Invoice_Customer_test.pdf");
		if(f.exists() && !f.isDirectory()) {
			isHere = true;
		}

		if(invoiceController.getPDF("test") == "IS HERE!!!!") {
			Assert.assertEquals(true,isHere);
		} else {
			Assert.assertEquals(false,isHere);
		}


	}

}
