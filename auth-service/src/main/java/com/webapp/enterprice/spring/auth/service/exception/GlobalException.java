package com.webapp.enterprice.spring.auth.service.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(CustomAuthenticationException.class)
    public ResponseEntity<ErrorDetail> handleCustomAuthenticationException(CustomAuthenticationException e) {
        ErrorDetail errorDetail = e.getErrorDetail();
        return new ResponseEntity<>(errorDetail, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CustomUserAlreadyExistException.class)
    public ResponseEntity<ErrorDetail> handleCustomUserAlreadyExistException(CustomUserAlreadyExistException e) {
        ErrorDetail errorDetail = e.getErrorDetail();
        return new ResponseEntity<>(errorDetail, HttpStatus.CONFLICT);
    }
}
