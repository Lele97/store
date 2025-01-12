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

    public ProductController() {
        super();
    }

    @Autowired
    private ProductService productService;

    /**
     * Retrieves all products.
     *
     * @return A ResponseEntity containing the list of all products.
     * <p>
     * This endpoint returns a list of all products available in the system.
     * The response body contains the product list with an HTTP 200 OK status.
     */
    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id The ID of the product to retrieve.
     * @return A ResponseEntity containing the product details.
     * <p>
     * This endpoint returns the details of a product specified by its ID.
     * If the product exists, it is returned in the response body with an HTTP 200 OK status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProducts(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    /**
     * Updates an existing product.
     *
     * @param id      The ID of the product to update.
     * @param product The product details to update.
     * @return A ResponseEntity containing a success message.
     * <p>
     * This endpoint updates the details of an existing product specified by its ID.
     * The updated product is saved, and a success message is returned with an HTTP 200 OK status.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        System.out.println("Product Controller:: " + product.getQuantity());
        Product product1 = productService.findById(id);
        product1.setQuantity(product.getQuantity());
        productService.update(product1);
        return ResponseEntity.ok("Product Updated");
    }

    /**
     * Adds a new product.
     *
     * @param productRequest The request object containing product details.
     * @return A ResponseEntity containing a success message.
     * <p>
     * This endpoint adds a new product to the system based on the provided ProductRequest.
     * The new product is saved, and a success message is returned with an HTTP 200 OK status.
     */
    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody ProductRequest productRequest) {
        productService.save(productRequest);
        return ResponseEntity.ok("Product added");
    }
}
