package com.webapp.enterprice.spring.auth.service.exception;

public class CustomUserAlreadyExistException extends RuntimeException {

    private ErrorDetail errorDetail;

    public CustomUserAlreadyExistException(ErrorDetail errorDetail) {
        super(errorDetail.getMessage());
        this.errorDetail = errorDetail;
    }

    public ErrorDetail getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(ErrorDetail errorDetail) {
        this.errorDetail = errorDetail;
    }
}
