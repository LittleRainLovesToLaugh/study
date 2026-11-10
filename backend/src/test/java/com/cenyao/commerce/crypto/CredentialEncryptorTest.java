package com.cenyao.commerce.crypto;

import com.cenyao.commerce.config.AppProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * 凭证加解密单测（不依赖 Spring 容器）。
 */
class CredentialEncryptorTest {

    @Test
    void encryptAndDecryptRoundTrip() {
        AppProperties props = new AppProperties();
        props.getCrypto().setAesKeyBase64("MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=");
        CredentialEncryptor encryptor = new CredentialEncryptor(props);

        String plain = "shopee-access-token-demo";
        String cipher = encryptor.encrypt(plain);
        assertNotEquals(plain, cipher);
        assertEquals(plain, encryptor.decrypt(cipher));
    }
}
