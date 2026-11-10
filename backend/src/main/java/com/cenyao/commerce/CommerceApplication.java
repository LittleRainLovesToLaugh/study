package com.cenyao.commerce;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 1688 → 虾皮跨境电商后台启动类。
 */
@SpringBootApplication
@MapperScan("com.cenyao.commerce")
public class CommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommerceApplication.class, args);
    }
}
