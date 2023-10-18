package com.lucas.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/greeting")
public class GreetingController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from API";
    }

    @GetMapping("/sayHelloProtected")
    public String sayHelloPro() {
        return "Hello from API protected";
    }
}
