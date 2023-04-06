package com.example.demo;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;
public class BouncyCastleService {

    public void init() {
        Security.addProvider(new BouncyCastleProvider())
    }
}
