package com.culinarycollege.service;

import com.culinarycollege.exception.ResourceNotFoundException;
import com.culinarycollege.model.entities.Ingredient;
import com.culinarycollege.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<Ingredient> getAll() {
        return ingredientRepository.findAll();
    }

    public Ingredient getById(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found: " + id));
    }

    public Ingredient create(Ingredient i) {
        i.id = null;
        return ingredientRepository.save(i);
    }

    public Ingredient update(Long id, Ingredient updated) {
        Ingredient existing = getById(id);
        existing.name = updated.name;
        existing.unit = updated.unit;
        return ingredientRepository.save(existing);
    }

    public void delete(Long id) {
        if (!ingredientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ingredient not found: " + id);
        }
        ingredientRepository.deleteById(id);
    }
}