package com.webapp.enterprice.spring.api_gateway_service.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    /**
     * Applies the custom GatewayFilter configuration.
     *
     * @param config The configuration object for the GatewayFilter.
     * @return The configured GatewayFilter that processes the request and response.
     *
     * This method validates the security of incoming requests. If a request is determined to be secured,
     * it checks for the presence of the Authorization header. If the Authorization header is missing,
     * an exception is thrown. If the Authorization header is present and starts with "Bearer ", the token
     * is extracted and validated. If the token is invalid, an exception is thrown.
     *
     */
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest modifiedRequest = exchange.getRequest();

            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    throw new RuntimeException("Invalid authorization header");
                }

                String token = authHeader.substring(7);
                try {
                    jwtUtil.validateToken(token);

                    // Extract user details from the token
                    String email = jwtUtil.extractEmail(token);
                    Set<String> roles = jwtUtil.extractRole(token);

                    // Log extracted roles for debugging
                    System.out.println("Extracted roles: " + roles);

                    if(roles.isEmpty()){
                        throw new Exception("No role found");
                    }

                    if(validator.isAdminEndpoint.test(exchange.getRequest())) {
                        if (!roles.contains("ADMIN")) {
                            throw new Exception("Admin role not allowed");
                        }
                    }

                    // Pass user details to downstream microservices
                    modifiedRequest = exchange.getRequest().mutate()
                            .header("loggedInUser", email)
                            .header("roles", String.join(",", roles))
                            .build();

                } catch (Exception e) {
                    System.out.println("Invalid access...!");
                    throw new RuntimeException("Unauthorized access to the application", e);
                }
            }

            return chain.filter(exchange.mutate().request(modifiedRequest).build());
        };
    }

    public static class Config { }
}
