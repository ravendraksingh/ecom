package com.niyava.ecom.orderservice.controller;

//import com.rks.orderservice.invoices.InvoiceGenerator;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = {"http://localhost:3080", "http://localhost:3090", "http://localhost:8080", "http://localhost:8090"})
@RestController
@RequestMapping("/api/v1/invoices")
public class InvoiceController {

//    @Autowired
//    private InvoiceGenerator invoiceGenerator;

    @GetMapping("/{orderid}")
    public String generateInvoice(@PathVariable String orderid) throws IOException {
//        invoiceGenerator.generateInvoice(orderid);
        return "Invoice generated!";
    }
}
