package com.example.learnclaudedemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class SystemController {

    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Map.of("message", "PetConnect Backend is running!", "status", "UP");
    }
}
