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

    /**
     * Retrieves an order by its ID.
     *
     * @param id The ID of the order to retrieve.
     * @return A ResponseEntity containing the order details wrapped in an Optional.
     *
     * This endpoint returns the details of an order specified by its ID. If the order exists,
     * it is returned in the response body with an HTTP 200 OK status. If the order does not exist,
     * an empty Optional is returned.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Order>> getOrders(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderByid(id));
    }

    /**
     * Creates a new order.
     *
     * @param orderRequest The request object containing order details.
     * @return A ResponseEntity containing a success message and HTTP status.
     *
     * This endpoint creates a new order based on the provided OrderRequest. If the order is created
     * successfully, it returns an HTTP 200 OK status with a success message. If the order creation
     * fails, it returns an HTTP 500 Internal Server Error status with the error message.
     */
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
