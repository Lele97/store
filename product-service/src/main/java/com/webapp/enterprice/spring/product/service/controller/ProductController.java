package com.webapp.enterprice.spring.product.service.controller;

import com.webapp.enterprice.spring.product.service.entity.Product;
import com.webapp.enterprice.spring.product.service.entity.ProductRequest;
import com.webapp.enterprice.spring.product.service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody ProductRequest productRequest) {
        productService.save(productRequest);
        return ResponseEntity.ok("Product added");
    }
}
