package com.webapp.enterprice.spring.auth.service.service;

import com.webapp.enterprice.spring.auth.service.entity.User;
import com.webapp.enterprice.spring.auth.service.entity.UserRequest;
import com.webapp.enterprice.spring.auth.service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserService userService;

    private UserRequest userRequest;

//    @BeforeEach
//    void setUp() {
//        userRequest = new UserRequest();
//        userRequest.setUsername("testuser");
//        userRequest.setPassword("password");
//        userRequest.setEmail("testuser@example.com");
//        userRequest.setRoles(Collections.singleton("ROLE_USER"));
//    }

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
