package com.webapp.enterprice.spring.api_gateway_service.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

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

    public static final List<String> adminEndpoints = List.of(
            "api/v1/products",
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
    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

    public Predicate<ServerHttpRequest> isAdminEndpoint = request -> adminEndpoints.stream().anyMatch(uri -> request.getURI().getPath().contains(uri));
}
