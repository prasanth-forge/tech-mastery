package com.prasanth.hello_spring.repository;

import com.prasanth.hello_spring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
