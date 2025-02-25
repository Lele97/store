package com.webapp.enterprice.spring.api_gateway_service.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * A list of open API endpoints that do not require security validation.
     * These endpoints are publicly accessible and do not need authorization headers.
     * <p>
     * Currently, the list includes:
     * - /api/v1/users/login
     * - /api/v1/users/register
     */
    public static final List<String> openApiEndpoints = List.of(
            "/api/v1/users/login",
            "/api/v1/users/register"
    );

    /**
     * A list of admin endpoints.
     * <p>
     * This list defines the endpoints that require admin access.
     * Currently, the list includes:
     * - api/v1/products
     * - /api/v1/products/**
     */
    public static final List<String> adminEndpoints = List.of(
            "/api/v1/products",
            "/api/v1/products/**");

    /**
     * A predicate to check if a request is secured.
     * <p>
     * This predicate tests whether the request's URI path does not match any of the open API endpoints.
     * If the request path is not in the open API endpoints list, it is considered secured and requires
     * security validation, such as checking for authorization headers.
     * <p>
     * Usage:
     * boolean isSecuredRequest = isSecured.test(serverHttpRequest);
     */
    Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
    /**
     * Predicate to check if a request is for an admin endpoint.
     * <p>
     * This predicate checks if the incoming request's URI matches any of the admin endpoints.
     */
    Predicate<ServerHttpRequest> isAdminEndpoint = request -> adminEndpoints.stream().anyMatch(uri -> pathMatcher.match(uri, request.getURI().getPath()));
}

