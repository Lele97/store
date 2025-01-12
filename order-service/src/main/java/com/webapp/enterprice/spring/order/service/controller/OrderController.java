package com.webapp.enterprice.spring.order.service.controller;

import com.webapp.enterprice.spring.order.service.entity.Order;
import com.webapp.enterprice.spring.order.service.entity.OrderRequest;
import com.webapp.enterprice.spring.order.service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Order>> getOrders(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderByid(id));
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequest) {
        try {
            orderService.AddOrder(orderRequest);
            return ResponseEntity.ok("Order created successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
