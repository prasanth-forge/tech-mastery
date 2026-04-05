package com.prasanth.hello_spring.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prasanth.hello_spring.model.User;
import com.prasanth.hello_spring.repository.UserRepository;
import com.prasanth.hello_spring.security.JwtUtil;
import com.prasanth.hello_spring.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private PasswordEncoder passwordEncoder;
    @MockitoBean
    private AuthenticationManager authenticationManager;
    @MockitoBean
    private JwtUtil jwtUtil;
    @MockitoBean
    private UserDetailsService userDetailsService;

    @Test
    void register_returnsUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        User saved = new User(1L, "Bob", "bob@prasanth-forge.com");
        when(userRepository.save(any(User.class))).thenReturn(saved);

        when(passwordEncoder.encode(any())).thenReturn("hashed-password");

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saved)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(saved.getName()))
                .andExpect(jsonPath("$.email").value(saved.getEmail()));
    }

    @Test
    void login_returnsToken() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail("alice@prasant-forge.com");
        authRequest.setPassword("password123");

        when(jwtUtil.generateToken(any())).thenReturn("mocked-jwt-token");

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("mocked-jwt-token"));
    }
}
