package com.cenyao.commerce.crypto;

import com.cenyao.commerce.common.BizException;
import com.cenyao.commerce.config.AppProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * 渠道凭证静态加密（AES-GCM）。密文入库，明文禁止写日志。
 */
@Component
public class CredentialEncryptor {

    private static final String TRANSFORM = "AES/GCM/NoPadding";
    private static final int GCM_TAG_BITS = 128;
    private static final int IV_BYTES = 12;

    private final byte[] keyBytes;
    private final SecureRandom secureRandom = new SecureRandom();

    public CredentialEncryptor(AppProperties appProperties) {
        this.keyBytes = Base64.getDecoder().decode(appProperties.getCrypto().getAesKeyBase64());
        if (keyBytes.length != 16 && keyBytes.length != 24 && keyBytes.length != 32) {
            throw new IllegalStateException("CRYPTO_AES_KEY_BASE64 解码后长度须为 16/24/32 字节");
        }
    }

    public String encrypt(String plainText) {
        if (plainText == null || plainText.isBlank()) {
            return plainText;
        }
        try {
            byte[] iv = new byte[IV_BYTES];
            secureRandom.nextBytes(iv);
            Cipher cipher = Cipher.getInstance(TRANSFORM);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, "AES"),
                    new GCMParameterSpec(GCM_TAG_BITS, iv));
            byte[] cipherBytes = cipher.doFinal(plainText.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            ByteBuffer buffer = ByteBuffer.allocate(iv.length + cipherBytes.length);
            buffer.put(iv);
            buffer.put(cipherBytes);
            return Base64.getEncoder().encodeToString(buffer.array());
        } catch (Exception e) {
            throw new BizException(50010, HttpStatus.INTERNAL_SERVER_ERROR, "凭证加密失败");
        }
    }

    public String decrypt(String cipherText) {
        if (cipherText == null || cipherText.isBlank()) {
            return cipherText;
        }
        try {
            byte[] all = Base64.getDecoder().decode(cipherText);
            ByteBuffer buffer = ByteBuffer.wrap(all);
            byte[] iv = new byte[IV_BYTES];
            buffer.get(iv);
            byte[] cipherBytes = new byte[buffer.remaining()];
            buffer.get(cipherBytes);
            Cipher cipher = Cipher.getInstance(TRANSFORM);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBytes, "AES"),
                    new GCMParameterSpec(GCM_TAG_BITS, iv));
            byte[] plain = cipher.doFinal(cipherBytes);
            return new String(plain, java.nio.charset.StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new BizException(50011, HttpStatus.INTERNAL_SERVER_ERROR, "凭证解密失败");
        }
    }

    /** 日志脱敏：仅保留前后各 2 位。 */
    public static String mask(String value) {
        if (value == null || value.length() < 6) {
            return "***";
        }
        return value.substring(0, 2) + "***" + value.substring(value.length() - 2);
    }
}
