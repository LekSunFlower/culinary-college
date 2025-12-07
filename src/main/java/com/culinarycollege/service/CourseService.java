package com.culinarycollege.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.culinarycollege.exception.ResourceNotFoundException;
import com.culinarycollege.model.entities.Course;
import com.culinarycollege.repository.CourseRepository;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    public Course getById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + id));
    }

    public Course create(Course c) {
        c.id = null;
        return courseRepository.save(c);
    }

    public Course create(String title, String description) {
        Course c = new Course();
        c.id = null; 
        c.title = title;
        c.description = description;
        return courseRepository.save(c);
    }

    public Course update(Long id, Course updated) {
        Course existing = getById(id);
        existing.title = updated.title;
        existing.description = updated.description;
        existing.teacher = updated.teacher;
        return courseRepository.save(existing);
    }

    public void delete(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course not found: " + id);
        }
        courseRepository.deleteById(id);
    }
}
