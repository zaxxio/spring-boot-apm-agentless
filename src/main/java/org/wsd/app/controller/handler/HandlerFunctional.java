package org.wsd.app.controller.handler;

import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.wsd.app.payload.Payload;

@RestControllerAdvice
public class HandlerFunctional {

    @ExceptionHandler(JwtValidationException.class)
    public Payload<String> handleError(JwtValidationException exception) {
        return new Payload.Builder<String>()
                .message("TOKEN EXPIRED")
                .payload(exception.getMessage())
                .build();
    }

}
