package com.culinarycollege.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.culinarycollege.exception.ResourceNotFoundException;
import com.culinarycollege.model.entities.Course;
import com.culinarycollege.model.entities.Lesson;
import com.culinarycollege.repository.LessonRepository;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final CourseService courseService;

    public LessonService(LessonRepository lessonRepository, CourseService courseService) {
        this.lessonRepository = lessonRepository;
        this.courseService = courseService;
    }

    public List<Lesson> findByCourse(Course course) {
        return lessonRepository.findByCourse(course);
    }

    public Lesson create(Long courseId, String title, String content) {
        Lesson l = new Lesson();
        l.title = title;
        l.content = content;
        l.course = courseService.getById(courseId);
        l.id = null;
        return lessonRepository.save(l);
    }

    public List<Lesson> getAll() {
        return lessonRepository.findAll();
    }

    public Lesson getById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found: " + id));
    }

    public Lesson create(Lesson l) {
        l.id = null;
        return lessonRepository.save(l);
    }

    public Lesson update(Long id, Lesson updated) {
        Lesson existing = getById(id);

        existing.title = updated.title;
        existing.content = updated.content;

        if (updated.course != null) {
            existing.course = updated.course;
        }

        return lessonRepository.save(existing);
    }

    public void delete(Long id) {
        if (!lessonRepository.existsById(id)) {
            throw new ResourceNotFoundException("Lesson not found: " + id);
        }
        lessonRepository.deleteById(id);
    }
}