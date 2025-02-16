package com.webapp.enterprice.spring.auth.service.service;

import com.webapp.enterprice.spring.auth.service.entity.Role;
import com.webapp.enterprice.spring.auth.service.entity.User;
import com.webapp.enterprice.spring.auth.service.entity.UserRequest;
import com.webapp.enterprice.spring.auth.service.repository.RoleRepository;
import com.webapp.enterprice.spring.auth.service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserService userService;

    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        userRequest = new UserRequest();
        userRequest.setUsername("testuser");
        userRequest.setPassword("password");
        userRequest.setEmail("testuser@example.com");

        Set<Map<String, Object>> roles = new HashSet<>();

        // Corrected structure: each role should have "id" and "name"
        Map<String, Object> adminRole = new HashMap<>();
        adminRole.put("id", 1L);  // Role ID must be a Long
        adminRole.put("name", "ROLE_ADMIN");

        Map<String, Object> userRole = new HashMap<>();
        userRole.put("id", 2L);
        userRole.put("name", "ROLE_USER");

        roles.add(adminRole);
        roles.add(userRole);

        userRequest.setRoles(roles);
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists() {
        when(repository.findByEmail(userRequest.getEmail())).thenReturn(Optional.of(new User()));

        assertThrows(Exception.class, () -> userService.registration(userRequest));

        verify(repository, times(1)).findByEmail(userRequest.getEmail());
        verify(repository, never()).save(any(User.class));
    }

    @Test
    void shouldRegisterNewUser() throws Exception {
        when(repository.findByEmail(userRequest.getEmail())).thenReturn(Optional.empty());
        when(bCryptPasswordEncoder.encode(userRequest.getPassword())).thenReturn("encryptedPassword");
        // Mock roleRepository behavior
        when(roleRepository.findById(1L)).thenReturn(Optional.of(new Role(1L, "ROLE_ADMIN")));

        userService.registration(userRequest);

        verify(repository, times(1)).findByEmail(userRequest.getEmail());
        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    void shouldHandleRegistrationFailure() {
        when(repository.findByEmail(userRequest.getEmail())).thenReturn(Optional.empty());
        when(bCryptPasswordEncoder.encode(userRequest.getPassword())).thenReturn("encryptedPassword");
        doThrow(new RuntimeException("Database error")).when(repository).save(any(User.class));

        Exception exception = assertThrows(Exception.class, () -> userService.registration(userRequest));

        assert (exception.getMessage().contains("Registration failed"));
    }
}
