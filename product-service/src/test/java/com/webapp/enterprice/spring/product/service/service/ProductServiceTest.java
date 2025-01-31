package com.webapp.enterprice.spring.product.service.service;

import com.webapp.enterprice.spring.product.service.entity.Product;
import com.webapp.enterprice.spring.product.service.entity.ProductRequest;
import com.webapp.enterprice.spring.product.service.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductRequest productRequest;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setQuantity(10);
        product.setPrice(100.0);

        productRequest = new ProductRequest();
        productRequest.setName("Test Product");
        productRequest.setQuantity(5);
        productRequest.setPrice(120.0);
    }

    @Test
    void shouldFindAllProducts() {
        List<Product> products = Arrays.asList(product);
        when(productRepository.findAllByOrderByIdAsc()).thenReturn(products);

        List<Product> result = productService.findAll();
        assertEquals(1, result.size());
        assertEquals("Test Product", result.get(0).getName());

        verify(productRepository, times(1)).findAllByOrderByIdAsc();
    }

    @Test
    void shouldFindProductById() {
        when(productRepository.getProductById(1L)).thenReturn(product);

        Product result = productService.findById(1L);
        assertNotNull(result);
        assertEquals("Test Product", result.getName());

        verify(productRepository, times(1)).getProductById(1L);
    }

    @Test
    void shouldSaveNewProduct() {
        when(productRepository.getProductByName("Test Product")).thenReturn(null);
        productService.save(productRequest);

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void shouldUpdateExistingProductQuantity() {
        when(productRepository.getProductByName("Test Product")).thenReturn(product);
        productService.save(productRequest);

        verify(productRepository, times(1)).save(any(Product.class));
        assertEquals(15, product.getQuantity());
        assertEquals(120.0, product.getPrice());
    }

    @Test
    void shouldUpdateProduct() {
        productService.update(product);

        verify(productRepository, times(1)).save(product);
    }
}
