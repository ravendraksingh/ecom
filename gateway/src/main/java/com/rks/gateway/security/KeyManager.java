package com.rks.gateway.security;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

@Component
public class KeyManager {
    private static Logger logger = LoggerFactory.getLogger(KeyManager.class);

    public static final String CERT_PATH = "/Users/ravendrasingh/mcommerce/gateway/src/main/resources/";
    public static final String CERT_FILE_NAME_FORMAT_CER = "Ravendra_Singh.cer";
    public static final String CERT_FILE_NAME_FORMAT_P12 = "Ravendra_Singh.p12";
    public static final String KEYSTORE_PASSWORD = "test123";
    public static final String KEYSTORE_KEY_ALIAS = "Ravendra Singh";
    /**
     * Keycloak public ket is retrieved using the below url
     * http://localhost:8080/auth/realms/mcommerce
     */
    public static final String KEYCLOAK_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsvnUvDays6Nz1dK1KqXfSO6PiJikxGIWbUSDXmVKrsnQLFl6oDYpBN0Gs8i2h8ULIOkDB7ePLalQJ57DBKbhDxI+mgDtRD2+isAH1v80dDvPzIhHGjqWlekqPR84HEXCM412Wqok/jP33+9oK02Nq00E0vHG1BuRt/4mK64e1RMXDGaUlzEq7jmjK1x1vPNMwuxV4FHsbduMRhVRQELxTwgEjGSvgnJek/873wycitf4gZ4jJF0hw4F+EPuRI1zqaBbIiO5BphFixUittZZ9CxwNMb18H4rfNU0gvuUD8aNucwtB3GBraQ868vpY+Nz/Zmm7kG9N4/D/7ioy5ElxjwIDAQAB";

    public static RSAPrivateKey getPrivateKey() throws Exception {
        String certPath = CERT_PATH;
        String fileName = CERT_FILE_NAME_FORMAT_P12;
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        RSAPrivateKey privateKey;
        keyStore.load(new FileInputStream(certPath + fileName), KEYSTORE_PASSWORD.toCharArray());
        privateKey = (RSAPrivateKey) keyStore.getKey(KEYSTORE_KEY_ALIAS, KEYSTORE_PASSWORD.toCharArray());
        //logger.info("RSAPrivateKey: " + privateKey.toString());
        //logger.info(privateKey.getFormat());
        return privateKey;
    }

    public static RSAPublicKey getPublicKey() throws Exception {
        String certFileName = CERT_FILE_NAME_FORMAT_CER;
        String certPath = CERT_PATH;
        FileInputStream fis = new FileInputStream(certPath + certFileName);
        byte[] keyBytes = new byte[fis.available()];
        fis.read(keyBytes);
        fis.close();
        String certStr = new String(keyBytes, "UTF-8");
        certStr = certStr.replaceAll("BEGIN CERTIFICATE", "")
                .replaceAll("END CERTIFICATE", "")
                .replaceAll("-", "")
                .trim();
        byte[] decodedValue = Base64.decodeBase64(certStr);
        ByteArrayInputStream instr = new ByteArrayInputStream(decodedValue);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate certObj = (X509Certificate) cf.generateCertificate(
                new FileInputStream(certPath + certFileName));
        RSAPublicKey pubKey = (RSAPublicKey) certObj.getPublicKey();
        return pubKey;
    }

    public static RSAPublicKey getKeycloakPublicKey() {
        String publicKeyPEM = KEYCLOAK_PUBLIC_KEY.replaceAll(System.lineSeparator(), "");
        byte[] encoded = Base64.decodeBase64(publicKeyPEM);

        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        RSAPublicKey publicKey = null;
        try {
            publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }
}
