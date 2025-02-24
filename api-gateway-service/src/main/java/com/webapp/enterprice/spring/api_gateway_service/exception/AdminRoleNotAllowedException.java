package com.webapp.enterprice.spring.api_gateway_service.exception;

public class AdminRoleNotAllowedException extends RuntimeException {
    public AdminRoleNotAllowedException(String message) {
        super(message);
    }
}
