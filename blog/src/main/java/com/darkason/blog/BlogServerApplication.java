package com.darkason.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.darkason.blog.dao")
public class BlogServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogServerApplication.class, args);
    }
}
