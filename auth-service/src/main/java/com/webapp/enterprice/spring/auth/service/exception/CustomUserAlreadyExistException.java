package com.webapp.enterprice.spring.auth.service.exception;

public class CustomUserAlreadyExistException extends RuntimeException {

    private final transient ErrorDetail errorDetail;

    public CustomUserAlreadyExistException(ErrorDetail errorDetail) {
        super(errorDetail.getMessage());
        this.errorDetail = errorDetail;
    }

    public ErrorDetail getErrorDetail() {
        return errorDetail;
    }
}
