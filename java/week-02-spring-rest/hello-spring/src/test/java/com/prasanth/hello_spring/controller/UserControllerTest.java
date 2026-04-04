package com.prasanth.hello_spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prasanth.hello_spring.model.User;
import com.prasanth.hello_spring.repository.UserRepository;
import com.prasanth.hello_spring.security.JwtUtil;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Test
    @WithMockUser
    void getAllUsers_returnsOk() throws Exception {
        when(userRepository.findAll()).thenReturn(List.of(new User()));

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @WithMockUser
    void getUserById_returnsUser() throws Exception {
        var user = new User(1L, "Alice", "alice@prasanth-forge.com");

        when(userRepository
                .findById(1L))
                .thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    @WithMockUser
    void getUserById_returnsNotFound() throws Exception {
        when(userRepository
                .findById(99L))
                .thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/users/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void createUser_returnsCreatedUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        User saved = new User(1L, "Bob", "bob@prasanth-forge.com");
        when(userRepository.save(any(User.class))).thenReturn(saved);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(saved))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(saved.getName()))
                .andExpect(jsonPath("$.email").value(saved.getEmail()));
    }
}
