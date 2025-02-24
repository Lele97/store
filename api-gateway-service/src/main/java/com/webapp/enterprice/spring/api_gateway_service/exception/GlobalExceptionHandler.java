package com.webapp.enterprice.spring.api_gateway_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorDetail> handleCustomException(CustomException e) {
        ErrorDetail errorDetail = e.getErrorDetail();
        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AdminRoleNotAllowedException.class)
    public ErrorDetail handleAdminRoleNotAllowedException(AdminRoleNotAllowedException e) {
        return new ErrorDetail("Admin Role Error", e.getMessage(), HttpStatus.FORBIDDEN.value());
    }

    @ExceptionHandler(RoleNotFoundCustomException.class)
    public ErrorDetail handleRoleNotFoundException(RoleNotFoundCustomException e) {
        return new ErrorDetail("Role Not Found", e.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(InvalidAuthorizationHeaderException.class)
    public ErrorDetail handleInvalidAuthorizationHeaderException(InvalidAuthorizationHeaderException e) {
        return new ErrorDetail("Invalid Authorization Header", e.getMessage(), HttpStatus.UNAUTHORIZED.value());
    }

    @ExceptionHandler(MissingAuthorizationRequestException.class)
    public ErrorDetail handleMissingAuthorizationRequestException(MissingAuthorizationRequestException e) {
        return new ErrorDetail("Missing Authorization Request", e.getMessage(), HttpStatus.UNAUTHORIZED.value());
    }
}
