package com.webapp.enterprice.spring.api_gateway_service.exception;

public class CustomException extends RuntimeException {
    private ErrorDetail errorDetail;

    public CustomException(ErrorDetail errorDetail) {
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
