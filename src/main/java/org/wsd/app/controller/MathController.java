package org.wsd.app.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.wsd.app.security.config.SecurityContextPropagator;


@RestController
@SecurityRequirement(name = "BEARER_TOKEN")
@RequestMapping("/api")
@Tag(name = "Math Controller")
public class MathController {

    @GetMapping("/random")
    public String sayRandom() {
        handleMe();
        return "Hello Mr. " + ThreadLocalRandom.current().nextInt(0, 10);
    }

    @Async
    public void handleMe() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        SecurityContextPropagator.propagate(executorService, () -> {
            // Code to be executed asynchronously with the original security context
            // This could include accessing secured resources, and the security context
            // will be maintained in the asynchronous task.
        });

        executorService.shutdown();

    }


}
