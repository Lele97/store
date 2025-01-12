package com.webapp.enterprice.spring.product.service.entity;

import lombok.Data;

@Data
public class ProductRequest {

    private String name;
    private double price;
    private int quantity;
}
