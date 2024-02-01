package org.wsd.app.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;
import org.wsd.app.security.jwt.JwtConfig;
import org.wsd.app.security.auth.resquest.SignInRequest;
import org.wsd.app.security.auth.resquest.SignUpRequest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Log
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Controller")
public class AuthenticationController {

    private final JwtConfig jwtConfig;
    private final JwtEncoder jwtEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationController(JwtConfig jwtConfig, JwtEncoder jwtEncoder, AuthenticationManager authenticationManager) {
        this.jwtConfig = jwtConfig;
        this.jwtEncoder = jwtEncoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(path = "/signIn", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String signIn(@Valid @RequestBody SignInRequest signInRequest) {

        final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                signInRequest.getUsername(),
                signInRequest.getPassword()
        );

        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        Instant now = Instant.now();
        Instant validity = now.plus(jwtConfig.getExpiration(), ChronoUnit.MINUTES);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject("CHROME")
                .build();

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS512).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    @PostMapping(path = "/signUp", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return "Hello World!";
    }

}
