package com.prasanth.hello_spring.repository;


import com.prasanth.hello_spring.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail_returnsUser() {
        User user = new User("Alice", "alice@prasanth-forge.com");
        userRepository.save(user);

        var result = userRepository.findByEmail(user.getEmail());
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo(user.getName());
    }

    @Test
    void findByEmail_returnsEmpty(){
        var result = userRepository.findByEmail("bob@prasanth-forge.com");
        assertThat(result).isNotPresent();
    }
}