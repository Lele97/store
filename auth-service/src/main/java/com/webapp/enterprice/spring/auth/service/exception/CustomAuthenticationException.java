package com.webapp.enterprice.spring.auth.service.exception;

public class CustomAuthenticationException extends RuntimeException {

    private final transient ErrorDetail errorDetail;

    public CustomAuthenticationException(ErrorDetail errorDetail) {
        super(errorDetail.getMessage());
        this.errorDetail = errorDetail;
    }

    public ErrorDetail getErrorDetail() {
        return errorDetail;
    }
}
