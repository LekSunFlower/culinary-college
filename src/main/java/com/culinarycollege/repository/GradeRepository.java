package com.culinarycollege.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.culinarycollege.model.entities.Grade;
import com.culinarycollege.model.entities.Lesson;
import com.culinarycollege.model.entities.Student;

public interface GradeRepository extends JpaRepository<Grade, Long> {

    List<Grade> findByStudent(Student student);
    List<Grade> findByLesson(Lesson lesson);

    Optional<Grade> findByStudentAndLesson(Student student, Lesson lesson);
}
