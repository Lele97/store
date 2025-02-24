package com.webapp.enterprice.spring.api_gateway_service.exception;

public class RoleNotFoundCustomException extends RuntimeException {

    public RoleNotFoundCustomException(String message) {
        super(message);
    }
}
