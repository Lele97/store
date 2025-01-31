package com.webapp.enterprice.spring.order.service.service;

import com.webapp.enterprice.spring.order.service.entity.Order;
import com.webapp.enterprice.spring.order.service.entity.OrderRequest;
import com.webapp.enterprice.spring.order.service.entity.Product;
import com.webapp.enterprice.spring.order.service.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OrderService orderService;

    private OrderRequest orderRequest;
    private Product product;

    @BeforeEach
    void setUp() {
        orderRequest = new OrderRequest();
        orderRequest.setUserId(String.valueOf(1L));
        orderRequest.setProductId(String.valueOf(1L));
        orderRequest.setQuantity(2);
        orderRequest.setStatus("PENDING");

        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setQuantity(10);
        product.setPrice(100.0);
    }

    @Test
    void shouldAddOrderSuccessfully() throws Exception {
        when(restTemplate.getForEntity(any(String.class), eq(Product.class), any(Long.class)))
                .thenReturn(new ResponseEntity<>(product, HttpStatus.OK));
        when(orderRepository.save(any(Order.class))).thenReturn(new Order());

        orderService.AddOrder(orderRequest);

        verify(orderRepository, times(1)).save(any(Order.class));
        verify(restTemplate, times(1)).put(any(String.class), any(Product.class), any(Long.class));
    }

    @Test
    void shouldThrowExceptionWhenProductDoesNotExist() {
        when(restTemplate.getForEntity(any(String.class), eq(Product.class), any(Long.class)))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

        Exception exception = assertThrows(Exception.class, () -> orderService.AddOrder(orderRequest));
        assert(exception.getMessage().contains("Product does not exist"));

        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void shouldThrowExceptionWhenQuantityNotAvailable() {
        product.setQuantity(1);
        when(restTemplate.getForEntity(any(String.class), eq(Product.class), any(Long.class)))
                .thenReturn(new ResponseEntity<>(product, HttpStatus.OK));

        Exception exception = assertThrows(Exception.class, () -> orderService.AddOrder(orderRequest));
        assert(exception.getMessage().contains("Quantity not available"));

        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void shouldHandleAddOrderFailure() {
        when(restTemplate.getForEntity(any(String.class), eq(Product.class), any(Long.class)))
                .thenReturn(new ResponseEntity<>(product, HttpStatus.OK));
        doThrow(new RuntimeException("Database error")).when(orderRepository).save(any(Order.class));

        Exception exception = assertThrows(Exception.class, () -> orderService.AddOrder(orderRequest));
        assert(exception.getMessage().contains("Add Order failed"));

        verify(orderRepository, times(1)).save(any(Order.class));
    }
}
