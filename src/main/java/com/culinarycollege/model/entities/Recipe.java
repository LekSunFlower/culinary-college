package com.culinarycollege.model.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "recipes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String title;

    @Column(length = 5000)
    public String instructions;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    public Lesson lesson;

    @JsonIgnore
    @OneToMany(mappedBy = "recipe")
    public List<RecipeIngredient> recipeIngredients;

    public Recipe() {
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getInstructions() { return instructions; }
    public Lesson getLesson() { return lesson; }
    public List<RecipeIngredient> getRecipeIngredients() { return recipeIngredients; }

}