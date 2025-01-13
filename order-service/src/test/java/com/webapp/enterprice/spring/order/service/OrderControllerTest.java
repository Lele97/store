package com.webapp.enterprice.spring.order.service;

import com.webapp.enterprice.spring.order.service.controller.OrderController;
import com.webapp.enterprice.spring.order.service.entity.Order;
import com.webapp.enterprice.spring.order.service.entity.OrderRequest;
import com.webapp.enterprice.spring.order.service.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;

    private OrderRequest orderRequest;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();

        orderRequest = new OrderRequest();
        orderRequest.setUserId(String.valueOf(1L));
        orderRequest.setProductId(String.valueOf(1L));
        orderRequest.setQuantity(2);
        orderRequest.setStatus("PENDING");
    }

    @Test
    void shouldGetOrderByIdSuccessfully() throws Exception {
        Order order = new Order();
        order.setId(1L);
        when(orderService.getOrderByid(1L)).thenReturn(Optional.of(order));

        mockMvc.perform(get("/api/v1/orders/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnEmptyWhenOrderNotFound() throws Exception {
        when(orderService.getOrderByid(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/orders/1"))
                .andExpect(status().isOk());
        verify(orderService, times(1)).getOrderByid(1L);
    }

    @Test
    void shouldCreateOrderSuccessfully() throws Exception {
        doNothing().when(orderService).AddOrder(any(OrderRequest.class));

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"productId\":1,\"quantity\":2,\"status\":\"PENDING\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Order created successfully"));

        verify(orderService, times(1)).AddOrder(any(OrderRequest.class));
    }

    @Test
    void shouldReturnInternalServerErrorOnOrderCreationFailure() throws Exception {
        doThrow(new Exception("Add Order failed")).when(orderService).AddOrder(any(OrderRequest.class));

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"productId\":1,\"quantity\":2,\"status\":\"PENDING\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Add Order failed"));

        verify(orderService, times(1)).AddOrder(any(OrderRequest.class));
    }
}
