package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/health")
    public String health() {
        try {
            BouncyCastleService bouncyCastleService = new BouncyCastleService();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return "Returned from finally";
        }
    }
}
