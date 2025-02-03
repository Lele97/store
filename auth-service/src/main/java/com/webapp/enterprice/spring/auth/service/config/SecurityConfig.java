package com.webapp.enterprice.spring.auth.service.config;

import com.webapp.enterprice.spring.auth.service.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    /**
     * List of endpoints that do not require authentication.
     * These endpoints are publicly accessible without any authorization.
     */
    private static final String[] AUTH_WHITELIST = {
            "/api/v1/users/login",
            "/api/v1/users/register",
            "/api/v1/users/test"
    };

    /**
     * Endpoint pattern that requires admin permissions.
     * Only users with the ROLE_ADMIN authority can access these endpoints.
     */
    private static final String[] ADMIN_PERMISSION = {"api/v1/products", "/api/v1/products/**"};


    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(); // Ensure UserInfoService implements UserDetailsService
    }

    /**
     * Configures the security filter chain for HTTP requests.
     *
     * @param http The HttpSecurity object to configure.
     * @return SecurityFilterChain The configured security filter chain.
     * @throws Exception If an error occurs during configuration.
     *                   <p>
     *                   This method disables CSRF protection, configures authorization rules for different endpoints,
     *                   sets the session management policy to stateless, and adds the JWT filter before the
     *                   UsernamePasswordAuthenticationFilter in the filter chain.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(AUTH_WHITELIST).permitAll()
                                .requestMatchers(ADMIN_PERMISSION).hasRole("ADMIN").anyRequest().authenticated())
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Password encoding
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
