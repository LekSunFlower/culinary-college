package com.culinarycollege.model.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "recipe_ingredients")
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    public Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    public Ingredient ingredient;

    public BigDecimal quantity;

    public RecipeIngredient() {
    }
}