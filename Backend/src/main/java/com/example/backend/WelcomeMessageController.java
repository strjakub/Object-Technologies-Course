package com.example.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeMessageController {

    @GetMapping
    public String greeting() {
        return "Hello World";
    }

}
