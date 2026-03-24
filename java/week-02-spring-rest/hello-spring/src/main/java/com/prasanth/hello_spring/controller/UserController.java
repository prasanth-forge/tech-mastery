package com.prasanth.hello_spring.controller;

import com.prasanth.hello_spring.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/users")
public class UserController {

    private final AtomicLong counter = new AtomicLong(3);

    private List<User> users = new ArrayList<>(List.of(new User(1L,
            "Alice", "alice@prasanth-forge.com"), new User(2L, "Bob", "bob" +
            "@prasanth-forge.com"), new User(3L, "Charlie", "charlie@prasanth-forge.com")));

    @GetMapping
    public ResponseEntity<List<User>> users() {
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> user(@PathVariable Long id) {
        return users
                .stream()
                .filter(u -> u.getId().equals((id)))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        try {
            if (users
                    .stream()
                    .filter(u -> u.getId().equals(user.getId()))
                    .findAny()
                    .isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            };

            User newUser = new User(
                    counter.incrementAndGet(),
                    user.getName(),
                    user.getEmail()
            );

            users.add(newUser);
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id,
                                       @RequestBody User updatedUser){
        try {
            return users.stream()
                    .filter(u -> u.getId().equals(id))
                    .findFirst()
                    .map(u -> {
                        u.setName(updatedUser.getName());
                        u.setEmail(updatedUser.getEmail());
                        return ResponseEntity.ok(u);
                    })
                    .orElse(ResponseEntity.notFound().build());

        } catch(Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        try {
            boolean removed = users.removeIf(u -> u.getId().equals(id));

            return removed ? ResponseEntity.noContent().build() :
                    ResponseEntity.notFound().build();
        } catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
