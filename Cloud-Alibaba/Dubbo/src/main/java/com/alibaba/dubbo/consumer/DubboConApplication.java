package com.alibaba.dubbo.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DubboConApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboConApplication.class, args);
    }

}
