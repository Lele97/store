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

    /**
     * Loads user-specific data by their email address.
     *
     * @param email The email address of the user to be loaded.
     * @return UserDetails The user details object containing user information.
     * @throws UsernameNotFoundException If the user with the given email is not found.
     *
     * This method overrides the loadUserByUsername method to fetch user details from the database
     * using the email address. It converts the User entity to UserDetails. If the user is not found,
     * it throws a UsernameNotFoundException.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userDetail = repository.findByEmail(email); // Assuming 'email' is used as username

        // Converting UserInfo to UserDetails
        return userDetail.map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }
}
