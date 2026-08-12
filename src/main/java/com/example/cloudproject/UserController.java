package com.example.cloudproject;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // CREATE - Add a new user
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    // READ - Get all users
    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    // READ - Get one user by ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // UPDATE - Update an existing user
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setName(user.getName());
                    existingUser.setEmail(user.getEmail());
                    return userRepository.save(existingUser);
                })
                .orElse(null);
    }

    // DELETE - Delete a user
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return "User deleted successfully";
        }

        return "User not found";
    }
}