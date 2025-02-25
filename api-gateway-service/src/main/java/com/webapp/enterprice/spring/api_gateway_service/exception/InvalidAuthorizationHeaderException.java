package com.webapp.enterprice.spring.api_gateway_service.exception;

public class InvalidAuthorizationHeaderException extends RuntimeException {
   public InvalidAuthorizationHeaderException(String message) {
       super(message);
   }
}
