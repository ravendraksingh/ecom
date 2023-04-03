package com.rks.orderservice.invoices;

import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Component;
import rst.pdfbox.layout.elements.Document;
import rst.pdfbox.layout.elements.Paragraph;
import rst.pdfbox.layout.elements.render.VerticalLayoutHint;
import rst.pdfbox.layout.text.Alignment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class InvoiceGenerator {

    public static final String INVOICE_PATH = "/Users/ravendrasingh/appdata/ecom/invoices/";

    public void generateInvoice(String orderId) throws IOException {
//        PDDocument document = new PDDocument();
        Document document = new Document(40, 40, 40, 60);
//        PDPage page = new PDPage();
//        document.addPage(page);

//        PDPageContentStream stream = new PDPageContentStream(document, page);
//        stream.setFont(PDType1Font.COURIER, 12);
//        stream.beginText();
//
//        stream.setLeading(14.5f);
//        stream.newLineAtOffset(25, 700);

        Paragraph p1 = new Paragraph();
        p1.addText("Hexcite eCommerce Private Limited", 22, PDType1Font.COURIER_BOLD);
        p1.setAlignment(Alignment.Center);
        document.add(p1, VerticalLayoutHint.CENTER);

//        stream.setFont(PDType1Font.COURIER_BOLD, 16);
//        stream.newLine();
//        String line1_1 = "Tax Invoice/Bill of Supply/Cash Memo";
//        stream.showText(line1_1);
//
//        stream.newLine();
//        String line1_2 = "(Original for Recipient)";
//        stream.showText(line1_2);
//
//        stream.newLine();
//        String line2 = "Order Id: " + orderId;
//        stream.showText(line2);
//
//        stream.newLine();
//        stream.setFont(PDType1Font.COURIER, 14);
//        String billingAddressHeading = "Billing Address";
//        stream.showText(billingAddressHeading);
//
//        stream.setFont(PDType1Font.COURIER, 12);
//        stream.newLine();
//        String ba1 = "Ravendra Singh";
//        stream.showText(ba1);
//
//        stream.newLine();
//        String ba2 = "C-304, Prateek Wisteria";
//        stream.showText(ba2);
//
//        stream.newLine();
//        String ba3 = "Sector-77, Noida, Uttar Pradesh";
//        stream.showText(ba3);
//
//        stream.newLine();
//        String ba4 = "Pin code - 201301, Mobile - 9910116004";
//        stream.showText(ba4);
//
//        stream.endText();
//        stream.close();
        String dateTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
//        document.save(INVOICE_PATH + "Invoice_" + orderId + "_" + dateTime + ".pdf");
//        document.close();
        final OutputStream outputStream =
                new FileOutputStream(INVOICE_PATH + "Invoice_" + orderId + "_" + dateTime + ".pdf");
        document.save(outputStream);
    }
}
