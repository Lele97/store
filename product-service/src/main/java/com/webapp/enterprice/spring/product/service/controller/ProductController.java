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

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProducts(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    public ProductController() {
        super();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id , @RequestBody Product product) {
        System.out.println("Product Controller:: "+product.getQuantity());
        Product product1 = productService.findById(id);
        product1.setQuantity(product.getQuantity());
        productService.update(product1);
        return ResponseEntity.ok("Product Updated");
    }

    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody ProductRequest productRequest) {
        productService.save(productRequest);
        return ResponseEntity.ok("Product added");
    }
}
