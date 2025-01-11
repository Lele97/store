package com.webapp.enterprice.spring.auth.service.service;

import com.webapp.enterprice.spring.auth.service.entity.User;
import com.webapp.enterprice.spring.auth.service.entity.UserRequest;
import com.webapp.enterprice.spring.auth.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;




@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private  UserRepository repository;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    public void registration(UserRequest userRequest) {
        try {
            User user = new User();
            user.setUsername(userRequest.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
            user.setEmail(userRequest.getEmail());
            user.setRoles(userRequest.getRoles());
            if (repository.findByEmail(user.getEmail()).isPresent()) {
                throw new Exception(userRequest.getUsername() + " already exists");
            }
            repository.save(user);
            //log.log(Level.INFO, "Registration successful");

        } catch (Exception e) {
            //log.log(Level.SEVERE, e.getMessage());
        }
    }
}
