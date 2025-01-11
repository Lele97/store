package com.webapp.enterprice.spring.auth.service.controller;

import com.webapp.enterprice.spring.auth.service.entity.AuthRequest;
import com.webapp.enterprice.spring.auth.service.entity.UserRequest;
import com.webapp.enterprice.spring.auth.service.jwt.JwtService;
import com.webapp.enterprice.spring.auth.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/users")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private  UserService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping(path = "/register")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void register(@RequestBody UserRequest userRequest) {
        service.registration(userRequest);
    }

    @PostMapping("/login")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getEmail());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}