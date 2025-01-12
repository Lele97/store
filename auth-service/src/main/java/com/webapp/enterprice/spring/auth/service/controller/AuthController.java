package com.webapp.enterprice.spring.auth.service.controller;

import com.webapp.enterprice.spring.auth.service.entity.AuthRequest;
import com.webapp.enterprice.spring.auth.service.entity.UserRequest;
import com.webapp.enterprice.spring.auth.service.jwt.JwtService;
import com.webapp.enterprice.spring.auth.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/users")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private UserService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Handles user registration.
     *
     * @param userRequest The request object containing user registration details.
     * @return A ResponseEntity containing a success message and HTTP status.
     *
     * This endpoint registers a new user by calling the registration service.
     * If registration is successful, it returns a HTTP 201 Created status.
     * If registration fails, it returns a HTTP 500 Internal Server Error status with the error message.
     */
    @PostMapping(path = "/register")
    public ResponseEntity<String> register(@RequestBody UserRequest userRequest) {
        try {
            service.registration(userRequest);
            return new ResponseEntity<>("Registration successful", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Authenticates a user and generates a JWT token.
     *
     * @param authRequest The request object containing user login details.
     * @return A ResponseEntity containing the JWT token and HTTP status.
     *
     * This endpoint authenticates the user by verifying their credentials.
     * If authentication is successful, it generates a JWT token and returns a HTTP 200 OK status.
     * If authentication fails, it returns a HTTP 401 Unauthorized status with an error message.
     */
    @PostMapping("/login")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(authRequest.getEmail());
                return new ResponseEntity<>(token, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid user request!", HttpStatus.UNAUTHORIZED);
            }
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Authentication failed: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}