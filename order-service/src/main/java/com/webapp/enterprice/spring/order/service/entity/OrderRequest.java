package com.webapp.enterprice.spring.order.service.entity;

import lombok.Data;

@Data
public class OrderRequest {

    private String userId;
    private String productId;
    private int quantity;
    private String status;
}
