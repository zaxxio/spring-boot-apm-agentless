package org.wsd.app.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Controller")
public class AuthenticationController {

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, path = "/authenticate")
    public String signIn() {
        log.info("I was called.");
        return "Hello World";
    }

}
