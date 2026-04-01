package com.prasanth.gateway_service.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserServiceClient {

    private final RestTemplate restTemplate;

    public UserServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<String> getUsers() {
        return restTemplate.exchange(
                "http://localhost:8091/api/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {
                }).getBody();
    }
}
