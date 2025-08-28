//package com.rks.orderservice.security;
//
//import auth.HmacUtils;
//import com.rks.orderservice.config.LocalClientConfig;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.tomcat.util.codec.binary.Base64;
//import org.bouncycastle.cert.jcajce.JcaCertStore;
//import org.bouncycastle.cms.CMSProcessableByteArray;
//import org.bouncycastle.cms.CMSSignedData;
//import org.bouncycastle.cms.CMSSignedDataGenerator;
//import org.bouncycastle.cms.CMSTypedData;
//import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
//import org.bouncycastle.crypto.tls.CipherType;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import org.bouncycastle.openssl.PEMKeyPair;
//import org.bouncycastle.openssl.PEMParser;
//import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
//import org.bouncycastle.operator.ContentSigner;
//import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
//import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
//import org.bouncycastle.util.Store;
//
//import javax.crypto.*;
//import javax.crypto.spec.SecretKeySpec;
//import java.io.*;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.security.*;
//import java.security.cert.CertificateException;
//import java.security.cert.CertificateFactory;
//import java.security.cert.X509Certificate;
//import java.security.interfaces.RSAPrivateKey;
//import java.security.interfaces.RSAPublicKey;
//import java.util.ArrayList;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.List;
//
//import static com.rks.orderservice.config.AppConfiguration.lookupVal;
//
//@Slf4j
//public class SecUtil {
//
//    private static SecUtil secUtil = new SecUtil();
//
//    public static InputStream getResourceStream(String certPath, String fileName) throws IOException {
//        Path certificatePath = Paths.get(certPath + "/" + fileName);
//        String publicKeyPath = Paths.get(certificatePath.toString()).toAbsolutePath().toString();
//        InputStream inputStream = new FileInputStream(publicKeyPath);
//        return inputStream;
//    }
//
//    public static HashMap<String, Object> loadKeyPair(InputStream cerFileStream, String password) throws Exception {
//        HashMap<String, Object> keyPair = new HashMap<>();
//
//        KeyStore keyStore = KeyStore.getInstance("PKCS12");
//        keyStore.load(cerFileStream, password.toCharArray());
//
//        Enumeration<String> keyStoreAliasEnum = keyStore.aliases();
//        String alias = "";
//        while (keyStoreAliasEnum.hasMoreElements()) {
//            alias = keyStoreAliasEnum.nextElement();
//            if (password != null) {
//                PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password.toCharArray());
//
//                X509Certificate x509Certificate = (X509Certificate) keyStore.getCertificate(alias);
//                PublicKey publicKey = x509Certificate.getPublicKey();
//
//                keyPair.put("Alias", alias);
//                keyPair.put("PublicKey", publicKey);
//                keyPair.put("PrivateKey", privateKey);
//                keyPair.put("X509Certificate", x509Certificate);
//            }
//        }
//        return keyPair;
//    }
//
//
//    public static PublicKey getPublicKey() throws Exception {
//        String certFileName = "Ravendra_Singh.cer";
//        String certPath = "/Users/ravendrasingh/projects/TestProjectJava/src/main/resources/certs/Ravendra_Singh.cer";
//        FileInputStream fis = new FileInputStream("/Users/ravendrasingh/projects/TestProjectJava/src/main/resources/certs/Ravendra_Singh.cer");
//        byte[] keyBytes = new byte[fis.available()];
//        fis.read(keyBytes);
//        fis.close();
//        String certStr = new String(keyBytes, "UTF-8");
//        certStr = certStr.replaceAll("BEGIN CERTIFICATE", "")
//                .replaceAll("END CERTIFICATE", "")
//                .replaceAll("-", "")
//                .trim();
//        byte[] decodedValue = org.apache.commons.codec.binary.Base64.decodeBase64(certStr);
//        ByteArrayInputStream instr = new ByteArrayInputStream(decodedValue);
//        CertificateFactory cf = CertificateFactory.getInstance("X.509");
//        //System.out.println(cf.getType() + "::"+ cf.getProvider() + "::" + cf.toString());
//        //X509Certificate certObj = (X509Certificate) cf.generateCertificate(instr);
//        X509Certificate certObj = (X509Certificate) cf.generateCertificate(
//                new FileInputStream("/Users/ravendrasingh/projects/TestProjectJava/src/main/resources/certs/Ravendra_Singh.cer"));
//        RSAPublicKey pubKey = (RSAPublicKey) certObj.getPublicKey();
//        //System.out.println("certObj.getSignature() :: " + certObj.getSignature().toString());
//        return pubKey;
//    }
//
//    public static PrivateKey getPrivateKey() throws Exception {
//        String certPath = "/Users/ravendrasingh/projects/TestProjectJava/src/main/resources/certs/";
//        String fileName = "Ravendra_Singh.p12";
//        KeyStore keyStore = KeyStore.getInstance("PKCS12");
//        RSAPrivateKey privateKey;
//        keyStore.load(new FileInputStream("/Users/ravendrasingh/projects/TestProjectJava/src/main/resources/certs/Ravendra_Singh.p12"), "test123".toCharArray());
//        privateKey = (RSAPrivateKey) keyStore.getKey("Ravendra Singh", "test123".toCharArray());
//        return privateKey;
//    }
//
//    public static HashMap<String, Object> loadKeyPair2(InputStream certFileStream, String certPassword) {
//        Security.addProvider(new BouncyCastleProvider());
//        HashMap<String, Object> keyPair = new HashMap<>();
//
//        try {
//            CertificateFactory factory = CertificateFactory.getInstance("X.509", "BC");
//            X509Certificate x509Certificate = (X509Certificate) factory.generateCertificate(certFileStream);
//
//            keyPair.put("X509Certificate", x509Certificate);
//            //log.info("X509Certificate = " + x509Certificate.getSigAlgName());
//
//            PublicKey publicKey = x509Certificate.getPublicKey();
//            keyPair.put("PublicKey", publicKey);
//            //log.info("PublicKey = " + keyPair.get("PublicKey"));
//
//            char[] keystorePassword = certPassword.toCharArray();
//            char[] keyPassword = certPassword.toCharArray();
//
//            KeyStore keyStore = KeyStore.getInstance("PKCS12");
//            keyStore.load(new FileInputStream("/Users/ravendrasingh/ecom/order-service/src/main/resources/certs/Ravendra_Singh.p12"), keystorePassword);
//
//            PrivateKey privateKey = (PrivateKey) keyStore.getKey("Ravendra Singh", keyPassword);
//
//            keyPair.put("PrivateKey", privateKey);
//            //log.info("PrivateKey = " + keyPair.get("PrivateKey"));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return keyPair;
//    }
//
//    public static String signData(String data, String clientId) throws Exception {
//        Security.addProvider(new BouncyCastleProvider());
////        log.info("clientId = " + clientId);
////        String certificatePath = lookupVal(clientId, "certificatePath");
////        String certFileName = lookupVal(clientId, "certFileName");
//
//        InputStream inputStream = getResourceStream(lookupVal(clientId, "certificatePath"), lookupVal(clientId, "certFileName"));
//        //HashMap<String, Object> keyPair = loadKeyPair(inputStream, lookupVal(clientId, "certificatePassword"));
//
//        HashMap<String, Object> keyPair = loadKeyPair2(inputStream, lookupVal(clientId, "certificatePassword"));
//
//        byte[] signedMessage = null;
//        List<X509Certificate> certList = new ArrayList<>();
//
//        CMSTypedData cmsData = new CMSProcessableByteArray(data.getBytes(StandardCharsets.UTF_8));
//
//        certList.add((X509Certificate) keyPair.get("X509Certificate"));
//
//        Store certs = new JcaCertStore(certList);
//        CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
//
////        log.info("######### PrivateKey " + keyPair.get("PrivateKey"));
//        ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256WithRSA").build((PrivateKey) keyPair.get("PrivateKey"));
//
//        generator.addSignerInfoGenerator(new JcaSignerInfoGeneratorBuilder(new JcaDigestCalculatorProviderBuilder().setProvider("BC")
//        .build()).build(contentSigner, (X509Certificate) keyPair.get("X509Certificate")));
//
//        generator.addCertificates(certs);
//
//        CMSSignedData cms = generator.generate(cmsData, true);
//
//        signedMessage = cms.getEncoded();
//        inputStream.close();
//        String signedData = Base64.encodeBase64String(signedMessage);
//        log.info("signedData = " + signedData);
//        return signedData;
//    }
//
//    public static String createHmac(String data, LocalClientConfig config) {
//        Mac sha256Hmac;
//        byte[] decodedKey = null;
//        String key;
//        SecretKeySpec spec;
//        PrivateKey privateKey;
//        byte[] macData = null;
//
//        try {
//            Path certificatePath = Paths.get(config.getCertificatePath());
//            String keyFolderPath = certificatePath.toAbsolutePath().toString();
//            String clientId = config.getClientId();
//            sha256Hmac = Mac.getInstance("HmacSHA256");
//            privateKey = secUtil.readPrivateKey(keyFolderPath + "/" + "_private.pem");
//            decodedKey = secUtil.decodeBase64StringToByte(secUtil.readSek(keyFolderPath + "/" + clientId + ".key"));
//            key = secUtil.decrypt(decodedKey, privateKey);
//            decodedKey = secUtil.decodeBase64StringToByte(key);
//            spec = new SecretKeySpec(decodedKey, "AES");
//            sha256Hmac.init(spec);
//            macData = sha256Hmac.doFinal(data.getBytes());
//        } catch (InvalidKeyException | NoSuchAlgorithmException | IOException | NoSuchPaddingException| IllegalBlockSizeException| BadPaddingException ex) {
//            log.error("Exception occurred in createHmac: {}", ex);
//        }
//        return new String(Base64.decodeBase64(macData));
//    }
//
//
//    private PrivateKey readPrivateKey(String privateKeyPath) throws IOException {
//        PEMParser pemParser = null;
//        PEMKeyPair pemKeyPair = null;
//        KeyPair keyPair = null;
//        BufferedReader bufferedReader = null;
//        try {
//            bufferedReader = new BufferedReader(new FileReader(privateKeyPath));
//            Security.addProvider(new BouncyCastleProvider());
//            pemParser = new PEMParser(bufferedReader);
//            pemKeyPair = (PEMKeyPair) pemParser.readObject();
//            keyPair = (new JcaPEMKeyConverter()).getKeyPair(pemKeyPair);
//            return keyPair.getPrivate();
//        } finally {
//            if (null != bufferedReader)
//                bufferedReader.close();
//            if (null != pemParser)
//                pemParser.close();
//        }
//    }
//
//    private String readSek(String sekKeyPath) throws IOException {
//        BufferedReader br = null;
//        try {
//            br = new BufferedReader(new FileReader(sekKeyPath));
//            StringBuilder sb = new StringBuilder();
//            String line = br.readLine();
//            while (line != null) {
//                sb.append(line);
//                line = br.readLine();
//            }
//            return sb.toString();
//        } finally {
//            if (br != null)
//                br.close();
//        }
//    }
//
//    private byte[] decodeBase64StringToByte(String stringData) {
//        return Base64.decodeBase64(stringData.getBytes(StandardCharsets.UTF_8));
//    }
//
//    private String decrypt(byte[] text, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
//        byte[] decryptedText = null;
//        Cipher cipher = Cipher.getInstance("RSA");
//        cipher.init(Cipher.DECRYPT_MODE, privateKey);
//        decryptedText = cipher.doFinal(text);
//        return new String(decryptedText);
//    }
//}
