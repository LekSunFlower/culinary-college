package com.culinarycollege.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.culinarycollege.exception.ResourceNotFoundException;
import com.culinarycollege.model.entities.Lesson;
import com.culinarycollege.model.entities.Recipe;
import com.culinarycollege.repository.RecipeRepository;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final LessonService lessonService;

    public RecipeService(RecipeRepository recipeRepository,
                         LessonService lessonService) {
        this.recipeRepository = recipeRepository;
        this.lessonService = lessonService;
    }

    public List<Recipe> getAll() {
        return recipeRepository.findAll();
    }

    public List<Recipe> findByLesson(Lesson lesson) {
        return recipeRepository.findByLesson(lesson);
    }

    public Recipe create(Long lessonId, String title, String description) {
        Recipe r = new Recipe();
        r.id = null;
        r.title = title;
        r.instructions = description;
        r.lesson = lessonService.getById(lessonId);

        return recipeRepository.save(r);
    }

    public Recipe create(Recipe r) {
        r.id = null;
        return recipeRepository.save(r);
    }

    public Recipe getById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found: " + id));
    }

    public Recipe update(Long id, Recipe updated) {
        Recipe existing = getById(id);

        existing.title = updated.title;
        existing.instructions = updated.instructions;
        existing.lesson = updated.lesson;

        return recipeRepository.save(existing);
    }

    public void delete(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recipe not found: " + id);
        }

        recipeRepository.deleteById(id);
    }
}
