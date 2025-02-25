package com.webapp.enterprice.spring.product.service.controller;

import com.webapp.enterprice.spring.product.service.entity.Product;
import com.webapp.enterprice.spring.product.service.entity.ProductRequest;
import com.webapp.enterprice.spring.product.service.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    private Product product;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setQuantity(10);
        product.setPrice(100.0);
    }

    @Test
    void shouldGetAllProductsSuccessfully() throws Exception {
        List<Product> products = Arrays.asList(product);
        when(productService.findAll()).thenReturn(products);

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'name':'Test Product','quantity':10,'price':100.0}]"));

        verify(productService, times(1)).findAll();
    }

    @Test
    void shouldGetProductByIdSuccessfully() throws Exception {
        when(productService.findById(1L)).thenReturn(product);

        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id':1,'name':'Test Product','quantity':10,'price':100.0}"));

        verify(productService, times(1)).findById(1L);
    }

    @Test
    void shouldUpdateProductSuccessfully() throws Exception {
        when(productService.findById(1L)).thenReturn(product);
        doNothing().when(productService).update(any(Product.class));

        mockMvc.perform(put("/api/v1/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"quantity\":5}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product Updated"));

        verify(productService, times(1)).findById(1L);
        verify(productService, times(1)).update(any(Product.class));
    }

    @Test
    void shouldAddProductSuccessfully() throws Exception {
        doNothing().when(productService).save(any(ProductRequest.class));

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Product\",\"quantity\":15,\"price\":50.0}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product added"));

        verify(productService, times(1)).save(any(ProductRequest.class));
    }
}
