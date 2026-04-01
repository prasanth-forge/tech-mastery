package com.prasanth.user_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping
    public ResponseEntity<List<String>> getUsers() {
        return ResponseEntity.ok(List.of("Alice", "Bob", "Charlie"));
    }
}
