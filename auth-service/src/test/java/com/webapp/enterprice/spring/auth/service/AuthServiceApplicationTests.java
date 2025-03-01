package com.webapp.enterprice.spring.auth.service;

import com.webapp.enterprice.spring.auth.service.controller.AuthController;
import com.webapp.enterprice.spring.auth.service.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest
class AuthServiceApplicationTests {

    @Autowired
    private UserService service;

    @Autowired
    private AuthController controller;

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
