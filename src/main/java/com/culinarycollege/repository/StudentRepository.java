package com.culinarycollege.repository;

import com.culinarycollege.model.entities.Student;
import com.culinarycollege.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByUser(User user);
}