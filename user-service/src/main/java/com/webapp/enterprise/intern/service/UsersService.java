package com.webapp.enterprise.intern.service;

import com.webapp.enterprise.intern.entity.UserRequest;
import com.webapp.enterprise.intern.entity.Users;
import com.webapp.enterprise.intern.jwt.JwtService;
import com.webapp.enterprise.intern.repository.UsersRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Level;

@Service
@Log
public class UsersService {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsersRepository usersRepository;

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

    public void registration(UserRequest userRequest) {

        try {
            Users user = new Users();
            user.setUsername(userRequest.getUsername());
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            user.setEmail(userRequest.getEmail());
            user.setRole(userRequest.getRole());

            if (!usersRepository.findByEmail(user.getEmail()).isEmpty()) {
                throw new Exception(userRequest.getUsername() + " already exists");
            }

            usersRepository.save(user);

            log.log(Level.INFO, "Registration successful");

        } catch (Exception e) {
            log.log(Level.WARNING, e.getMessage());
        }
    }
}
