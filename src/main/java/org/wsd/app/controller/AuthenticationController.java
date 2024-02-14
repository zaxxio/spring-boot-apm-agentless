package org.wsd.app.controller;

import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.wsd.app.payload.Payload;
import org.wsd.app.security.auth.AuthenticationService;
import org.wsd.app.security.auth.response.SignInResponse;
import org.wsd.app.security.auth.resquest.SignInRequest;
import org.wsd.app.security.auth.resquest.SignUpRequest;

@Log
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Controller")
public class AuthenticationController {
    private final MeterRegistry meterRegistry;
    private final AuthenticationService authenticationService;

    public AuthenticationController(MeterRegistry meterRegistry, AuthenticationService authenticationService) {
        this.meterRegistry = meterRegistry;
        this.authenticationService = authenticationService;
    }

    @PostMapping(path = "/signIn", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Payload<SignInResponse> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        return authenticationService.signIn(signInRequest);
    }

    @PostMapping(path = "/signUp", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return "Hello World!";
    }

}
