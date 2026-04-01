package com.prasanth.gateway_service.controller;

import com.prasanth.gateway_service.client.UserServiceClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceClient userServiceClient;

    public UserController(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @GetMapping
    public ResponseEntity<List<String>> getUsers() {
        return ResponseEntity.ok(userServiceClient.getUsers());
    }
}
