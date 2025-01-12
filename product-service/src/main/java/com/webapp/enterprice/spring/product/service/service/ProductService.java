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

    public void update(Product product) {
        System.out.println("Product Service:: "+product.getQuantity());
        productRepository.save(product);
    }

}
