package com.darkason.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EmailServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmailServerApplication.class, args);
    }
}
