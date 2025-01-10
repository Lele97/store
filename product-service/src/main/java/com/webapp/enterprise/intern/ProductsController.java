package com.webapp.enterprise.intern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductsController {
    public static void main(String[] args) {
        SpringApplication.run(ProductsController.class, args);
    }
}
