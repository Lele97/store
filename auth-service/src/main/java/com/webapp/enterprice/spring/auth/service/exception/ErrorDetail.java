package com.webapp.enterprice.spring.auth.service.exception;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ErrorDetail implements Serializable {
    @java.io.Serial
    private static final long serialVersionUID = -7034897190745766939L;

    private String message;
    private String errorType;
    private String errorCode;

}
