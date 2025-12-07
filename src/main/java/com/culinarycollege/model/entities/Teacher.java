package com.culinarycollege.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "teachers")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String fullName;

    public String specialization;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    public User user;

    @JsonIgnore
    @OneToMany(mappedBy = "teacher")
    public List<Course> courses;

    public Teacher() {
    }
}
