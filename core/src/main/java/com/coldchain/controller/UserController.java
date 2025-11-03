package com.coldchain.controller;

import com.coldchain.model.User;
import com.coldchain.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User newUser) {
        // WARNING: This saves passwords in plain text or as-is.
        // A real app should hash the password here.
        newUser.setUser_id(0);
        return userRepository.save(newUser);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User updatedUser) {
        return userRepository.findById(id)
            .map(existingUser -> {
                updatedUser.setUser_id(id);
                // Handle password: if field is empty, don't update it
                if (updatedUser.getPassword_hash() == null || updatedUser.getPassword_hash().isEmpty()) {
                    updatedUser.setPassword_hash(existingUser.getPassword_hash());
                }
                User savedUser = userRepository.save(updatedUser);
                return ResponseEntity.ok(savedUser);
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}