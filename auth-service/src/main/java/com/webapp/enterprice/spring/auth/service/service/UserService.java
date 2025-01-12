package com.webapp.enterprice.spring.auth.service.service;

import com.webapp.enterprice.spring.auth.service.entity.User;
import com.webapp.enterprice.spring.auth.service.entity.UserRequest;
import com.webapp.enterprice.spring.auth.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    /**
     * Service for registration new User
     *
     * @param userRequest
     * @throws Exception
     */
    public void registration(UserRequest userRequest) throws Exception {
        if (repository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new Exception(userRequest.getUsername() + " already exists");
        }
        try {
            User user = new User();
            user.setUsername(userRequest.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
            user.setEmail(userRequest.getEmail());
            user.setRoles(userRequest.getRoles());
            repository.save(user);
        } catch (Exception e) {
            throw new Exception("Registration failed: " + e.getMessage(), e);
        }
    }
}
