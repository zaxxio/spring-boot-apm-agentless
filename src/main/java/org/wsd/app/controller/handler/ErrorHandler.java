package org.wsd.app.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.wsd.app.payload.Payload;
import org.wsd.app.payload.PayloadStatus;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Payload<String> ha() {
        return new Payload.Builder<String>()
                .status(PayloadStatus.UNAUTHORIZED)
                .message("Spring Boot")
                .payload("asas")
                .build();
    }

}
