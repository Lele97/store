package com.webapp.enterprice.spring.api_gateway_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AdminRoleNotAllowedException.class)
    public ResponseEntity<ErrorDetail> handleAdminRoleNotAllowedException(AdminRoleNotAllowedException e) {
        ErrorDetail errorDetail = new ErrorDetail("Admin Role Error", e.getMessage(), HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(errorDetail, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(RoleNotFoundCustomException.class)
    public ResponseEntity<ErrorDetail> handleRoleNotFoundException(RoleNotFoundCustomException e) {
        ErrorDetail errorDetail = new ErrorDetail("Role Not Found", e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(InvalidAuthorizationHeaderException.class)
    public ResponseEntity<ErrorDetail> handleInvalidAuthorizationHeaderException(InvalidAuthorizationHeaderException e) {
        ErrorDetail errorDetail = new ErrorDetail("Invalid Authorization Header", e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingAuthorizationRequestException.class)
    public ResponseEntity<ErrorDetail> handleMissingAuthorizationRequestException(MissingAuthorizationRequestException e) {
        ErrorDetail errorDetail = new ErrorDetail("Missing Authorization Request", e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }
}
