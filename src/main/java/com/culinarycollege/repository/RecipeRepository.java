package com.culinarycollege.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.culinarycollege.model.entities.Lesson;
import com.culinarycollege.model.entities.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByLesson(Lesson lesson);

}