package org.wsd.app.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.wsd.app.payload.Payload;
import org.wsd.app.security.auth.AuthenticationService;
import org.wsd.app.security.auth.response.SignInResponse;
import org.wsd.app.security.auth.response.SignUpResponse;
import org.wsd.app.security.auth.resquest.SignInRequest;
import org.wsd.app.security.auth.resquest.SignUpRequest;

@Log
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Controller")
public class AuthenticationController {
    private final MeterRegistry meterRegistry;
    private final AuthenticationService authenticationService;

    public AuthenticationController(MeterRegistry meterRegistry,
                                    AuthenticationService authenticationService) {
        this.meterRegistry = meterRegistry;
        this.authenticationService = authenticationService;
    }

    @Operation(description = "Sign In", summary = "Endpoint for user sign in")
    @PostMapping(path = "/signIn",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    /*@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SignUpResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = jakarta.validation.Payload.class)))
    })*/
    public Payload<SignInResponse> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        Counter counter = Counter.builder("TOTAL_SIGN_IN_REQUESTS").register(meterRegistry);
        counter.increment();
        return authenticationService.signIn(signInRequest);
    }

    @PostMapping(path = "/signUp", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return "Hello World!";
    }

}
