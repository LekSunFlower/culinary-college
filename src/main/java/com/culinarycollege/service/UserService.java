package com.culinarycollege.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.culinarycollege.exception.ResourceNotFoundException;
import com.culinarycollege.model.entities.User;
import com.culinarycollege.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
    }

    public User create(User u) {
        if (u.getPassword() == null || u.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        u.setId(null);
        u.setPassword(passwordEncoder.encode(u.getPassword()));

        return userRepository.save(u);
    }

    public User update(Long id, User updated) {
        User existing = getById(id);

        existing.setUsername(updated.getUsername());

        if (updated.getPassword() != null && !updated.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(updated.getPassword()));
        }

        existing.setRole(updated.getRole());

        return userRepository.save(existing);
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found: " + id);
        }
        userRepository.deleteById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
    }
}
