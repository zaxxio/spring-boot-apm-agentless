package org.wsd.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@RestController
@SecurityRequirement(name = "BEARER_TOKEN")
@RequestMapping("/api")
public class MathController {

    @GetMapping("/random")
    public String sayRandom() {
        return "Hello Mr. " + ThreadLocalRandom.current().nextInt(0, 10);
    }

}
