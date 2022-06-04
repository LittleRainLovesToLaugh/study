package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Field;

/**
 * Description
 *
 * @author 小雨
 * createTime 2022年05月28日 21:57:00
 */
@SpringBootApplication
public class StreamMQMain8801 {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        SpringApplication.run(StreamMQMain8801.class, args);
    }
}
