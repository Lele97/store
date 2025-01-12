package com.webapp.enterprice.spring.product.service.service;

import com.webapp.enterprice.spring.product.service.entity.Product;
import com.webapp.enterprice.spring.product.service.entity.ProductRequest;
import com.webapp.enterprice.spring.product.service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public List<Product> findAll() {
        return productRepository.findAllByOrderByIdAsc();
    }

    public Product findById(Long id) {
        return productRepository.getProductById(id);
    }

    /**
     * Saves a new product to the system or updates an existing product if it already exists.
     *
     * @param productRequest The request object containing product details.
     *
     * This method creates a new Product object based on the provided ProductRequest.
     * If a product with the same name already exists, it updates the quantity and price of the existing product.
     * Otherwise, it saves the new product to the repository.
     */
    public void save(ProductRequest productRequest) {
        Product newProduct = new Product();
        newProduct.setName(productRequest.getName());
        newProduct.setPrice(productRequest.getPrice());
        newProduct.setQuantity(productRequest.getQuantity());

        // Assuming findByName returns an Optional<Product>
        Optional<Product> existingProductOptional = Optional.ofNullable(productRepository.getProductByName(newProduct.getName()));

        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();
            existingProduct.setQuantity(existingProduct.getQuantity() + productRequest.getQuantity());
            existingProduct.setPrice(productRequest.getPrice());
            productRepository.save(existingProduct);
        } else {
            productRepository.save(newProduct);
        }
    }

    /**
     * Updates an existing product in the system.
     *
     * @param product The product details to update.
     *
     * This method updates the details of an existing product in the system.
     * The updated product is saved to the repository.
     */
    public void update(Product product) {
        System.out.println("Product Service:: "+product.getQuantity());
        productRepository.save(product);
    }

}
