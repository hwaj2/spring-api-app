package com.app;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.junit.jupiter.api.Test;

public class JasyptTest {

    @Test
    public void jasyptTest() {
        String password = "sjekjlkse#$esrel22!lelf11434^efdf";
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setPoolSize(4);
        encryptor.setPassword(password);
        encryptor.setAlgorithm("PBEWithMD5AndTripleDES");

        String content = "9c9uJh1XDJMe72tcz8o94Z1pqQxRrUdKaxFAlScPFuIGZtp57tFR3Q";    // 암호화 할 내용
        String encryptedContent = encryptor.encrypt(content); // 암호화
        String decryptedContent = encryptor.decrypt(encryptedContent); // 복호화

        System.out.println("Enc : " + encryptedContent + ", Dec: " + decryptedContent);
    }
}
