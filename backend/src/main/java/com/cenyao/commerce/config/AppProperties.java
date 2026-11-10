package com.cenyao.commerce.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 应用配置绑定（凭证均为占位，真实值走环境变量）。
 */
@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private Jwt jwt = new Jwt();
    private Crypto crypto = new Crypto();
    private Minio minio = new Minio();
    private Alibaba1688 alibaba1688 = new Alibaba1688();
    private Shopee shopee = new Shopee();
    private XxlJob xxlJob = new XxlJob();

    @Data
    public static class Jwt {
        private String secret;
        private long expireSeconds = 86400;
    }

    @Data
    public static class Crypto {
        /** AES-256 密钥 Base64 编码 */
        private String aesKeyBase64;
    }

    @Data
    public static class Minio {
        private String endpoint;
        private String accessKey;
        private String secretKey;
        private String bucket;
    }

    @Data
    public static class Alibaba1688 {
        private String appKey;
        private String appSecret;
        private String apiBaseUrl;
    }

    @Data
    public static class Shopee {
        private long partnerId;
        private String partnerKey;
        private String apiBaseUrl;
    }

    @Data
    public static class XxlJob {
        private String adminAddresses;
        private String appName;
        private String accessToken;
    }
}
