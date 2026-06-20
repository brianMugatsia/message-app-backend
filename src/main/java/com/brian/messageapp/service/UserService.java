package com.brian.messageapp.service;

import com.brian.messageapp.model.User;
import com.brian.messageapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // Create new user
    public User create(User user) {
        return userRepository.save(user);
    }

    // Get all users
    public List<User> getAll() {
        return userRepository.findAll();
    }

    // Get user by ID
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Get user by email
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
