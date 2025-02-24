package com.webapp.enterprice.spring.api_gateway_service.exception;

public class MissingAuthorizationRequestException extends RuntimeException {

    public MissingAuthorizationRequestException(String message) {
        super(message);
    }
}
