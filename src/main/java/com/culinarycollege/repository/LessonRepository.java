package com.culinarycollege.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.culinarycollege.model.entities.Course;
import com.culinarycollege.model.entities.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByCourse(Course course);
}
