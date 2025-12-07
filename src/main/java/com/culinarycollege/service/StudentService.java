package com.culinarycollege.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.culinarycollege.exception.ResourceNotFoundException;
import com.culinarycollege.model.entities.Student;
import com.culinarycollege.model.entities.User;
import com.culinarycollege.repository.StudentRepository;
import com.culinarycollege.repository.UserRepository;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public StudentService(StudentRepository studentRepository,
                        UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public Student getById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + id));
    }

    public Student create(Student s) {
        s.setId(null);

        Long userId = s.getUser().getId();
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
        s.setUser(user);

        return studentRepository.save(s);
    }

    public Student update(Long id, Student updated) {
        Student existing = getById(id);

        existing.setFullName(updated.getFullName());
        existing.setEnrollmentDate(updated.getEnrollmentDate());
        existing.setUser(updated.getUser());

        return studentRepository.save(existing);
    }

    public void delete(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found: " + id);
        }
        studentRepository.deleteById(id);
    }

    public Student findByUser(User user) {
        return studentRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found for user: " + user.getUsername()));
    }
}
