package com.webapp.enterprice.spring.auth.service.controller;

import com.webapp.enterprice.spring.auth.service.config.CustomUserDetails;
import com.webapp.enterprice.spring.auth.service.config.HasiCorpVaultSecretData;
import com.webapp.enterprice.spring.auth.service.entity.AuthRequest;
import com.webapp.enterprice.spring.auth.service.entity.UserRequest;
import com.webapp.enterprice.spring.auth.service.exception.CustomUserAlreadyExistException;
import com.webapp.enterprice.spring.auth.service.exception.ErrorDetail;
import com.webapp.enterprice.spring.auth.service.jwt.JwtService;
import com.webapp.enterprice.spring.auth.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

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

    private final HasiCorpVaultSecretData hasiCorpVaultSecretData;


    /**
     * Handles user registration.
     *
     * @param userRequest The request object containing user registration details.
     * @return A ResponseEntity containing a success message and HTTP status.
     * <p>
     * This endpoint registers a new user by calling the registration service.
     * If registration is successful, it returns a HTTP 201 Created status.
     * If registration fails, it returns a HTTP 500 Internal Server Error status with the error message.
     */
    @PostMapping(path = "/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserRequest userRequest) {
        try {
            service.registration(userRequest);
            Map<String, String> response = new HashMap<>();
            response.put("code", String.valueOf(HttpStatus.CREATED));
            response.put("message", "User registered successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ErrorDetail error = new ErrorDetail();
            error.setMessage("User registration failed");
            error.setErrorCode(String.valueOf(HttpStatus.CONFLICT));
            error.setErrorType(e.getMessage());
            throw new CustomUserAlreadyExistException(error);
        }
    }

    /**
     * Authenticates a user and generates a JWT token.
     *
     * @param authRequest The request object containing user login details.
     * @return A ResponseEntity containing the JWT token and HTTP status.
     * <p>
     * This endpoint authenticates the user by verifying their credentials.
     * If authentication is successful, it generates a JWT token and returns a HTTP 200 OK status.
     * If authentication fails, it returns a HTTP 401 Unauthorized status with an error message.
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);
        Map<String, String> response = new HashMap<>();
        response.put("code", String.valueOf(HttpStatus.OK));
        response.put("token", token);
        response.put("expiresIn", "1800");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Tests the authentication and returns user and role information.
     *
     * @return A ResponseEntity containing user and role information and HTTP status.
     * <p>
     * This endpoint retrieves the authentication details from the security context,
     * extracts the user and role information, and returns it with a HTTP 200 OK status.
     */
    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> test() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        authorities.forEach(authority -> Logger.getLogger("Role: " + authority.getAuthority()));
        Map<String, String> response = new HashMap<>();
        response.put("code", String.valueOf(HttpStatus.OK));
        response.put("user", ((CustomUserDetails) authentication.getPrincipal()).getUsername());
        response.put("role", authorities.toString());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/secrets")
    public String getSecrets() {
        return "Username: " + hasiCorpVaultSecretData.getUsername() + ", Password: " + hasiCorpVaultSecretData.getPassword();
    }
}