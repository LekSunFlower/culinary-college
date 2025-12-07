package com.culinarycollege.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.culinarycollege.exception.ResourceNotFoundException;
import com.culinarycollege.model.entities.Grade;
import com.culinarycollege.model.entities.Lesson;
import com.culinarycollege.model.entities.Student;
import com.culinarycollege.repository.GradeRepository;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;
    private final StudentService studentService;
    private final LessonService lessonService;

    public GradeService(GradeRepository gradeRepository,
                        StudentService studentService,
                        LessonService lessonService) {
        this.gradeRepository = gradeRepository;
        this.studentService = studentService;
        this.lessonService = lessonService;
    }

    public List<Grade> getAll() {
        return gradeRepository.findAll();
    }

    public Grade getById(Long id) {
        return gradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grade not found: " + id));
    }

    public Grade create(Grade g) {
        g.id = null;
        return gradeRepository.save(g);
    }

    public Grade update(Long id, Grade updated) {
        Grade existing = getById(id);
        existing.value = updated.value;
        existing.student = updated.student;
        existing.lesson = updated.lesson;
        return gradeRepository.save(existing);
    }

    public void delete(Long id) {
        if (!gradeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Grade not found: " + id);
        }
        gradeRepository.deleteById(id);
    }

    public List<Grade> findByStudent(Student student) {
        return gradeRepository.findByStudent(student);
    }

    public List<Grade> findByLesson(Lesson lesson) {
        return gradeRepository.findByLesson(lesson);
    }


    public Grade setGrade(Long studentId, Long lessonId, Integer value) {

        var student = studentService.getById(studentId);
        var lesson = lessonService.getById(lessonId);

        var existing = gradeRepository.findByStudentAndLesson(student, lesson)
                .orElse(null);

        if (existing != null) {
            existing.value = value;
            return gradeRepository.save(existing);
        }

        Grade g = new Grade();
        g.student = student;
        g.lesson = lesson;
        g.value = value;

        return gradeRepository.save(g);
    }
}
