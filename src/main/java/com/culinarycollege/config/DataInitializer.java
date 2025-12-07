package com.culinarycollege.config;

import com.culinarycollege.model.entities.User;
import com.culinarycollege.model.enums.Role;
import com.culinarycollege.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initUsers(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                userRepository.save(new User("admin", "admin", Role.ADMIN));
            }
            if (userRepository.findByUsername("teacher").isEmpty()) {
                userRepository.save(new User("teacher", "teacher", Role.TEACHER));
            }
            if (userRepository.findByUsername("student").isEmpty()) {
                userRepository.save(new User("student", "student", Role.STUDENT));
            }
        };
    }
}