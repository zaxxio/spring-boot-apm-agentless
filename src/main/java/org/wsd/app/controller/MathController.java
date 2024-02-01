package org.wsd.app.controller;

import io.micrometer.tracing.annotation.NewSpan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

@RestController
public class MathController {

    @GetMapping("/random")
    @NewSpan
    public String sayRandom() {
        return "Hello Mr. " + ThreadLocalRandom.current().nextInt(0, 10);
    }

}
