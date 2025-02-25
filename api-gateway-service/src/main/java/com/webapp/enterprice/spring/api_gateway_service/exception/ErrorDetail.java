package com.webapp.enterprice.spring.api_gateway_service.exception;

import java.io.Serial;
import java.io.Serializable;

public class ErrorDetail implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String message;
    private String errorType;
    private int errorCode;

    public ErrorDetail(String message, String errorType, int errorCode) {
        this.message = message;
        this.errorType = errorType;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
