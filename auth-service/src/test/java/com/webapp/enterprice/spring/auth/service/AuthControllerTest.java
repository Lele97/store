package com.webapp.enterprice.spring.auth.service;

import com.webapp.enterprice.spring.auth.service.controller.AuthController;
import com.webapp.enterprice.spring.auth.service.entity.AuthRequest;
import com.webapp.enterprice.spring.auth.service.entity.UserRequest;
import com.webapp.enterprice.spring.auth.service.jwt.JwtService;
import com.webapp.enterprice.spring.auth.service.service.UserService;
import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@Log4j
public class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

//    @Test
//    void shouldRegisterUserSuccessfully() throws Exception {
//        UserRequest userRequest = new UserRequest();
//        userRequest.setUsername("testuser");
//        userRequest.setPassword("password");
//        userRequest.setEmail("testuser@example.com");
//        userRequest.setRoles(Collections.singleton("ROLE_USER"));
//
//        mockMvc.perform(post("/api/v1/users/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"username\":\"testuser\",\"password\":\"password\",\"email\":\"testuser@example.com\",\"roles\":\"ROLE_USER\"}"))
//                .andExpect(status().isCreated())
//                .andExpect(content().string("Registration successful"));
//
//        verify(userService, times(1)).registration(any(UserRequest.class));
//    }
//
//    @Test
//    void shouldReturnInternalServerErrorOnRegistrationFailure() throws Exception {
//        UserRequest userRequest = new UserRequest();
//        userRequest.setUsername("testuser");
//        userRequest.setPassword("password");
//        userRequest.setEmail("testuser@example.com");
//        userRequest.setRoles("ROLE_USER");
//
//        doThrow(new Exception("Registration failed")).when(userService).registration(any(UserRequest.class));
//
//        mockMvc.perform(post("/api/v1/users/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"username\":\"testuser\",\"password\":\"password\",\"email\":\"testuser@example.com\",\"roles\":\"ROLE_USER\"}"))
//                .andExpect(status().isInternalServerError())
//                .andExpect(content().string("Registration failed"));
//
//        verify(userService, times(1)).registration(any(UserRequest.class));
//    }

//    @Test
//    void shouldAuthenticateAndGenerateTokenSuccessfully() throws Exception {
//        AuthRequest authRequest = new AuthRequest();
//        authRequest.setEmail("testuser@example.com");
//        authRequest.setPassword("password");
//
//        Authentication authentication = mock(Authentication.class);
//        when(authentication.isAuthenticated()).thenReturn(true);
//        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
//        when(jwtService.generateToken(authRequest.getEmail())).thenReturn("jwt-token");
//
//        mockMvc.perform(post("/api/v1/users/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"email\":\"testuser@example.com\",\"password\":\"password\"}"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("jwt-token"));
//
//        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
//        verify(jwtService, times(1)).generateToken(authRequest.getEmail());
//    }

    @Test
    void shouldReturnUnauthorizedOnAuthenticationFailure() throws Exception {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail("testuser@example.com");
        authRequest.setPassword("password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new AuthenticationException("Authentication failed") {});

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"testuser@example.com\",\"password\":\"password\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Authentication failed: Authentication failed"));

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}
