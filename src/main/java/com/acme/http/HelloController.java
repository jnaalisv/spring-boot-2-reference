package com.acme.http;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/com/acme")
    public String home() {
        return "Hello World!";
    }
}