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
@Table(name = "lessons")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String title;

    @Column(length = 5000)
    public String content;

    @ManyToOne
    @JoinColumn(name = "course_id")
    public Course course;

    @JsonIgnore
    @OneToMany(mappedBy = "lesson")
    public List<Recipe> recipes;

    @JsonIgnore
    @OneToMany(mappedBy = "lesson")
    public List<Grade> grades;

    public Lesson() {
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public Course getCourse() { return course; }

    public List<Recipe> getRecipes() { return recipes; }
    public List<Grade> getGrades() { return grades; }

}