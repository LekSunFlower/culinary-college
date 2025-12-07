package com.culinarycollege.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.culinarycollege.model.entities.Course;
import com.culinarycollege.model.entities.Grade;
import com.culinarycollege.model.entities.Ingredient;
import com.culinarycollege.model.entities.Lesson;
import com.culinarycollege.model.entities.Recipe;
import com.culinarycollege.model.entities.Student;
import com.culinarycollege.model.entities.Teacher;
import com.culinarycollege.model.entities.User;
import com.culinarycollege.service.CourseService;
import com.culinarycollege.service.GradeService;
import com.culinarycollege.service.IngredientService;
import com.culinarycollege.service.LessonService;
import com.culinarycollege.service.RecipeService;
import com.culinarycollege.service.StudentService;
import com.culinarycollege.service.TeacherService;
import com.culinarycollege.service.UserService;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final UserService userService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final CourseService courseService;
    private final LessonService lessonService;
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final GradeService gradeService;

    public ApiController(UserService userService,
                         StudentService studentService,
                         TeacherService teacherService,
                         CourseService courseService,
                         LessonService lessonService,
                         RecipeService recipeService,
                         IngredientService ingredientService,
                         GradeService gradeService) {
        this.userService = userService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.courseService = courseService;
        this.lessonService = lessonService;
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.gradeService = gradeService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User getUser(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public User createUser(@RequestBody User user) {

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        return userService.create(user);
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User updateUser(@PathVariable Long id, @RequestBody User updated) {
        return userService.update(id, updated);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/students")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    public List<Student> getAllStudents() {
        return studentService.getAll();
    }

    @GetMapping("/students/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    public Student getStudent(@PathVariable Long id) {
        return studentService.getById(id);
    }

    @PostMapping("/students")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public Student createStudent(@RequestBody Student s) {
        return studentService.create(s);
    }

    @PutMapping("/students/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student updated) {
        return studentService.update(id, updated);
    }

    @DeleteMapping("/students/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteStudent(@PathVariable Long id) {
        studentService.delete(id);
    }

    @GetMapping("/teachers")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    public List<Teacher> getAllTeachers() {
        return teacherService.getAll();
    }

    @GetMapping("/teachers/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    public Teacher getTeacher(@PathVariable Long id) {
        return teacherService.getById(id);
    }

    @PostMapping("/teachers")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public Teacher createTeacher(@RequestBody Teacher t) {
        return teacherService.create(t);
    }

    @PutMapping("/teachers/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Teacher updateTeacher(@PathVariable Long id, @RequestBody Teacher updated) {
        return teacherService.update(id, updated);
    }

    @DeleteMapping("/teachers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteTeacher(@PathVariable Long id) {
        teacherService.delete(id);
    }

    @GetMapping("/courses")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    public List<Course> getAllCourses() {
        return courseService.getAll();
    }

    @GetMapping("/courses/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    public Course getCourse(@PathVariable Long id) {
        return courseService.getById(id);
    }

    @PostMapping("/courses")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public Course createCourse(@RequestBody Course c) {
        return courseService.create(c);
    }

    @PutMapping("/courses/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public Course updateCourse(@PathVariable Long id, @RequestBody Course updated) {
        return courseService.update(id, updated);
    }

    @DeleteMapping("/courses/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public void deleteCourse(@PathVariable Long id) {
        courseService.delete(id);
    }

    @GetMapping("/lessons")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    public List<Lesson> getAllLessons() {
        return lessonService.getAll();
    }

    @GetMapping("/lessons/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    public Lesson getLesson(@PathVariable Long id) {
        return lessonService.getById(id);
    }

    @PostMapping("/lessons")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public Lesson createLesson(@RequestBody Lesson l) {
        return lessonService.create(l);
    }

    @PutMapping("/lessons/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public Lesson updateLesson(@PathVariable Long id, @RequestBody Lesson updated) {
        return lessonService.update(id, updated);
    }

    @DeleteMapping("/lessons/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public void deleteLesson(@PathVariable Long id) {
        lessonService.delete(id);
    }

    @GetMapping("/recipes")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    public List<Recipe> getAllRecipes() {
        return recipeService.getAll();
    }

    @GetMapping("/recipes/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    public Recipe getRecipe(@PathVariable Long id) {
        return recipeService.getById(id);
    }

    @PostMapping("/recipes")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public Recipe createRecipe(@RequestBody Recipe r) {
        return recipeService.create(r);
    }

    @PutMapping("/recipes/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public Recipe updateRecipe(@PathVariable Long id, @RequestBody Recipe updated) {
        return recipeService.update(id, updated);
    }

    @DeleteMapping("/recipes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public void deleteRecipe(@PathVariable Long id) {
        recipeService.delete(id);
    }

    @GetMapping("/ingredients")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    public List<Ingredient> getAllIngredients() {
        return ingredientService.getAll();
    }

    @GetMapping("/ingredients/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    public Ingredient getIngredient(@PathVariable Long id) {
        return ingredientService.getById(id);
    }

    @PostMapping("/ingredients")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public Ingredient createIngredient(@RequestBody Ingredient i) {
        return ingredientService.create(i);
    }

    @PutMapping("/ingredients/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public Ingredient updateIngredient(@PathVariable Long id, @RequestBody Ingredient updated) {
        return ingredientService.update(id, updated);
    }

    @DeleteMapping("/ingredients/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public void deleteIngredient(@PathVariable Long id) {
        ingredientService.delete(id);
    }

    @GetMapping("/grades")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    public List<Grade> getAllGrades() {
        return gradeService.getAll();
    }

    @GetMapping("/grades/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    public Grade getGrade(@PathVariable Long id) {
        return gradeService.getById(id);
    }

    @PostMapping("/grades")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public Grade createGrade(@RequestBody Grade g) {
        return gradeService.create(g);
    }

    @PutMapping("/grades/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public Grade updateGrade(@PathVariable Long id, @RequestBody Grade updated) {
        return gradeService.update(id, updated);
    }

    @DeleteMapping("/grades/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public void deleteGrade(@PathVariable Long id) {
        gradeService.delete(id);
    }
}