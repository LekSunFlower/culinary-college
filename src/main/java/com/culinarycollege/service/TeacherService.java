package com.culinarycollege.service;

import com.culinarycollege.exception.ResourceNotFoundException;
import com.culinarycollege.model.entities.Teacher;
import com.culinarycollege.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getAll() {
        return teacherRepository.findAll();
    }

    public Teacher getById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found: " + id));
    }

    public Teacher create(Teacher t) {
        t.id = null;
        return teacherRepository.save(t);
    }

    public Teacher update(Long id, Teacher updated) {
        Teacher existing = getById(id);
        existing.fullName = updated.fullName;
        existing.specialization = updated.specialization;
        existing.user = updated.user;
        return teacherRepository.save(existing);
    }

    public void delete(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new ResourceNotFoundException("Teacher not found: " + id);
        }
        teacherRepository.deleteById(id);
    }
}