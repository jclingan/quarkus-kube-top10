package org.acme;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/hello")
public class ExampleResource {

    @ConfigProperty(name = "greeting")
    String greeting;

    @GetMapping
    public String hello() {
        return greeting;
    }
}