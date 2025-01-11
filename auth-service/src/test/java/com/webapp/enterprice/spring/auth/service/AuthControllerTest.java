package com.webapp.enterprice.spring.auth.service;

import com.webapp.enterprice.spring.auth.service.controller.AuthController;
import com.webapp.enterprice.spring.auth.service.entity.AuthRequest;
import com.webapp.enterprice.spring.auth.service.entity.UserRequest;
import com.webapp.enterprice.spring.auth.service.jwt.JwtService;
import com.webapp.enterprice.spring.auth.service.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(AuthController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc; // Used to perform HTTP requests in tests

    @Mock
    private UserService userService; // Mocked UserService for dependency injection

    @Mock
    private JwtService jwtService; // Mocked JwtService for dependency injection

    @Mock
    private AuthenticationManager authenticationManager; // Mocked AuthenticationManager for dependency injection

    @Mock
    private Authentication authentication; // Mocked Authentication object for testing

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes mocks before each test
    }

    /**
     * Test for the /register endpoint.
     * This test verifies that a new user can be registered successfully.
     *
     * @throws Exception
     */
    @Test
    @Order(1)
    void testRegister() throws Exception {
        // Create a mock UserRequest object
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("testuser");
        userRequest.setPassword("password");
        userRequest.setEmail("test@example.com");

        // Mock the registration method to do nothing
        doNothing().when(userService).registration(any(UserRequest.class));

        // Perform a POST request to /api/v1/users/register with the user data
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testuser\",\"password\":\"password\",\"email\":\"test@example.com\"}"))
                .andExpect(status().isCreated()) // Expect HTTP 201 Created status
                .andExpect(content().string("Registration successful")); // Expect the success message

        // Verify that the registration method was called once with the provided UserRequest
        verify(userService, times(1)).registration(any(UserRequest.class));
    }

    /**
     * Test for the /login endpoint.
     * This test verifies that a user can log in and receive a JWT token.
     *
     * @throws Exception
     */
    @Test
    @Order(2)
    void testAuthenticateAndGetToken() throws Exception {
        // Create a mock AuthRequest object
        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail("test@example.com");
        authRequest.setPassword("password");

        // Mock the authentication process and token generation
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jwtService.generateToken(anyString())).thenReturn("jwt-token");

        // Perform a POST request to /api/v1/users/login with the auth data
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\",\"password\":\"password\"}"))
                .andExpect(status().isOk()) // Expect HTTP 200 OK status
                .andExpect(content().string("jwt-token")); // Expect the JWT token

        // Verify that the authentication and token generation methods were called once
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, times(1)).generateToken("test@example.com");
    }

    /**
     * Test for the /login endpoint with invalid credentials.
     * This test verifies that an invalid login attempt returns an appropriate error message.
     *
     * @throws Exception
     */
    @Test
    @Order(3)
    void testAuthenticateAndGetToken_Failure() throws Exception {
        // Create a mock AuthRequest object with invalid credentials
        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail("invalid@example.com");
        authRequest.setPassword("wrongpassword");

        // Mock the authentication process to throw an exception
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Invalid credentials") {});

        // Perform a POST request to /api/v1/users/login with the invalid auth data
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"invalid@example.com\",\"password\":\"wrongpassword\"}"))
                .andExpect(status().isUnauthorized()) // Expect HTTP 401 Unauthorized status
                .andExpect(content().string("Authentication failed: Invalid credentials")); // Expect the failure message

        // Verify that the authentication method was called once
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}
