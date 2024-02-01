package org.wsd.app.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wsd.app.jwt.JwtConfig;
import org.wsd.app.security.auth.SignInRequest;
import org.wsd.app.security.auth.SignUpRequest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Log
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Controller")
public class AuthenticationController {

    private final JwtConfig jwtConfig;
    private final JwtEncoder jwtEncoder;

    public AuthenticationController(JwtConfig jwtConfig, JwtEncoder jwtEncoder) {
        this.jwtConfig = jwtConfig;
        this.jwtEncoder = jwtEncoder;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/signIn")
    public String signIn(@RequestBody SignInRequest signInRequest) {
        Instant now = Instant.now();
        Instant validity = now.plus(1, ChronoUnit.MINUTES);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject("CHROME")
                .build();

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS512).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/signUp")
    public String signUp(@RequestBody SignUpRequest signUpRequest) {
        return "Hello World!";
    }

}
