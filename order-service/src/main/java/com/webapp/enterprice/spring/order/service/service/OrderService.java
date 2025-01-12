package com.webapp.enterprice.spring.order.service.service;

import com.webapp.enterprice.spring.order.service.entity.Order;
import com.webapp.enterprice.spring.order.service.entity.OrderRequest;
import com.webapp.enterprice.spring.order.service.entity.Product;
import com.webapp.enterprice.spring.order.service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Optional<Order> getOrderByid(Long id) {
        return orderRepository.findById(id);
    }

    /**
     * Adds a new order to the system.
     *
     * @param orderRequest The request object containing order details.
     * @throws Exception If an error occurs during the order creation process.
     *
     * This method processes an order request by performing the following steps:
     * 1. Fetches the product details using the product ID from the order request.
     * 2. Checks if the product exists. If not, throws an exception.
     * 3. Checks if the requested quantity is available. If not, throws an exception.
     * 4. Creates a new Order object and saves it to the repository.
     * 5. Updates the product quantity after the order is placed.
     *
     * If any exception occurs during these steps, it is caught and rethrown with an error message.
     */
    public void AddOrder(OrderRequest orderRequest) throws Exception {
        try {

            // Fetch the product details
            Product product = restTemplate.getForEntity("http://localhost:9093/api/v1/products/{id}", Product.class, orderRequest.getProductId()).getBody();

            // Check if the product does not exist
            if (product == null) {
                throw new Exception("Product does not exist");
            }

            // Check if the quantity requested is available
            if (orderRequest.getQuantity() > product.getQuantity()) {
                throw new Exception("Quantity not available");
            }

            // Create a new order
            Order orderToAdd = new Order();
            orderToAdd.setUserId(orderRequest.getUserId());
            orderToAdd.setProductId(orderRequest.getProductId());
            orderToAdd.setQuantity(orderRequest.getQuantity());
            orderToAdd.setStatus(orderRequest.getStatus());
            orderRepository.save(orderToAdd);

            // Update product quantity
            int newQuantity = product.getQuantity() - orderRequest.getQuantity();
            product.setQuantity(newQuantity);
            System.out.println("Order Service Controller:: "+product.getQuantity());
            restTemplate.put("http://localhost:9093/api/v1/products/{id}", product, orderRequest.getProductId());

        } catch (Exception e) {
            throw new Exception("Add Order failed: " + e.getMessage(), e);
        }
    }
}
