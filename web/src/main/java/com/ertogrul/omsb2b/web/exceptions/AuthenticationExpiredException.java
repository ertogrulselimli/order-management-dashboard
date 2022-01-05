package com.ertogrul.omsb2b.web.exceptions;

import org.springframework.http.HttpStatus;

public class AuthenticationExpiredException extends RuntimeException {

    private HttpStatus httpStatus;

    private String message;

    public AuthenticationExpiredException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
