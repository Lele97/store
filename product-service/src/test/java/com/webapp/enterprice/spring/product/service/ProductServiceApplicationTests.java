package com.webapp.enterprice.spring.product.service;

import com.webapp.enterprice.spring.product.service.controller.ProductController;
import com.webapp.enterprice.spring.product.service.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest
class ProductServiceApplicationTests {

	@Autowired
	private ProductService service;

	@Autowired
	private ProductController controller;

	@Value("${db.password}")
	private String dbPassword;

	@Value("${db.username}")
	private String dbusername;

	@Test
	void contextLoads() {

		// Check that beans are properly loaded
		assertNotNull("UserService bean should not be null", service);
		assertNotNull("AuthController bean should not be null", controller);

		// Verify configuration properties
		assertNotNull("DB Password should not be null", dbPassword);
		assertNotNull("DB Username should not be null", dbusername);
	}
}
