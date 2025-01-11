package com.webapp.enterprice.spring.auth.service.config;

import com.webapp.enterprice.spring.auth.service.entity.User;
import com.webapp.enterprice.spring.auth.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userDetail = repository.findByEmail(email); // Assuming 'email' is used as username

        // Converting UserInfo to UserDetails
        return userDetail.map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }
}
